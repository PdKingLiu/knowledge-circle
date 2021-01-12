package com.pdking.commonListPage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post
import com.competition.pdking.lib_base.com.competition.pdking.bean.UserData
import com.competition.pdking.lib_base.com.competition.pdking.util.DataUtil
import com.competition.pdking.lib_common_resourse.toast.ToastUtils
import com.pdking.commonListPage.adapter.PostAdapter
import com.pdking.detail.post_details.view.PostDetailActivity
import com.pdking.profile.R
import kotlinx.android.synthetic.main.profile_list_fragment_layout.*


class PostListFragment : Fragment() {

    private var postAdapter: PostAdapter? = null
    private var postList: ArrayList<Post>? = null
    private var title = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        postList = ArrayList()
        postAdapter = PostAdapter(postList, context)
        postAdapter?.setListener { _: View?, i: Int ->
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra("postId", postList?.get(i)?.objectId)
            startActivity(intent)
        }
        postListRv?.layoutManager = LinearLayoutManager(context)
        postListRv?.adapter = postAdapter
        loadData()
    }

    private fun loadData() {
        title = arguments?.getString("title") ?: ""
        if (title == "收藏列表" || title == "浏览历史") {
            val kind = if (title == "收藏列表") {
                "collectList"
            } else {
                "scanList"
            }
            DataUtil.getUserData(arguments?.getString("userId") ?: "") {
                if (it != null) {
                    val query: BmobQuery<Post> = BmobQuery<Post>()
                    val userData = UserData()
                    userData.objectId = it.objectId
                    query.order("-createdAt")
                    query.include("author,scan")
                    query.addWhereRelatedTo(kind, BmobPointer(userData))
                    query.findObjects(object : FindListener<Post>() {
                        override fun done(list: List<Post>, e: BmobException?) {
                            if (e == null) {
                                hideLoading()
                                postList?.clear()
                                postList?.addAll(ArrayList(list))
                                Handler(Looper.getMainLooper()).post { postAdapter?.notifyDataSetChanged() }
                            } else {
                                hideLoading()
                                ToastUtils.showToast(this@PostListFragment.context, "加载失败")
                            }
                        }
                    })
                } else {
                    hideLoading()
                    ToastUtils.showToast(this@PostListFragment.context, "加载失败")
                }
            }
            return
        }
        val postBmobQuery = BmobQuery<Post>()
        postBmobQuery.addWhereEqualTo("author", arguments?.getString("userId") ?: "")
        postBmobQuery.order("-createdAt")
        postBmobQuery.include("author,scan")
        postBmobQuery.findObjects(object : FindListener<Post>() {
            override fun done(list: List<Post>?, e: BmobException?) {
                hideLoading()
                if (e == null || list != null) {
                    postList?.clear()
                    postList?.addAll(ArrayList(list))
                    Handler(Looper.getMainLooper()).post { postAdapter?.notifyDataSetChanged() }
                } else {
                    ToastUtils.showToast(this@PostListFragment.context, "加载失败")
                }
            }
        })

    }

    private fun showLoading() {
        loadingBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_list_fragment_layout, container, false)
    }

    companion object {
        fun newFragment(userId: String, title: String): Fragment {
            return PostListFragment().apply {
                arguments = Bundle().apply {
                    putString("userId", userId)
                    putString("title", title)
                }
            }
        }
    }
}