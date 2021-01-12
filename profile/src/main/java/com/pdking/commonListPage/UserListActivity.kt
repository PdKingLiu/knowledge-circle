package com.pdking.commonListPage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.competition.pdking.lib_base.BaseActivity
import com.competition.pdking.lib_base.com.competition.pdking.bean.User
import com.competition.pdking.lib_base.com.competition.pdking.util.DataUtil
import com.competition.pdking.lib_common_resourse.toast.ToastUtils
import com.pdking.commonListPage.adapter.UserAdapter
import com.pdking.profile.R
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : BaseActivity() {

    private var userId = ""
    private var title = ""
    private var userAdapter: UserAdapter? = null
    private var userList: ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        title = intent.getStringExtra("title")
        userId = intent.getStringExtra("userId")
        userListTitle.text = title
        userList = ArrayList()
        userAdapter = UserAdapter(userList, this)
        userListRv?.layoutManager = LinearLayoutManager(this)
        userListRv?.adapter = userAdapter
        showLoading("加载中...")
        loadDate()
    }

    private fun loadDate() {
        val kind = when (title) {
            "关注列表" -> {
                "attentionList"
            }
            "粉丝列表" -> {
                "fansList"
            }
            else -> {
                ""
            }
        }
        DataUtil.getUserData(userId) {
            if (it != null) {
                val query: BmobQuery<User> = BmobQuery<User>()
                query.order("-createdAt")
                query.include("author,scan")
                query.addWhereRelatedTo(kind, BmobPointer(it))
                query.findObjects(object : FindListener<User>() {
                    override fun done(list: List<User>, e: BmobException?) {
                        hideLoading()
                        if (e == null) {
                            userList?.clear()
                            userList?.addAll(ArrayList(list))
                            Handler(Looper.getMainLooper()).post { userAdapter?.notifyDataSetChanged() }
                        } else {
                            ToastUtils.showToast(this@UserListActivity, "加载失败")
                        }
                    }
                })
            } else {
                ToastUtils.showToast(this@UserListActivity, "加载失败")
            }
        }
    }
}
