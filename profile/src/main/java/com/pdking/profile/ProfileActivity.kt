package com.pdking.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.datatype.BmobRelation
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.competition.pdking.lib_base.BaseActivity
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post
import com.competition.pdking.lib_base.com.competition.pdking.bean.User
import com.competition.pdking.lib_base.com.competition.pdking.bean.UserData
import com.competition.pdking.lib_base.com.competition.pdking.util.DataUtil
import com.competition.pdking.lib_common_resourse.constant.Constant
import com.pdking.commonListPage.PostListActivity
import com.pdking.commonListPage.PostListFragment
import com.pdking.commonListPage.UserListActivity
import com.pdking.profile.wight.ProfileLikeCountDialog
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.math.abs

class ProfileActivity : BaseActivity(), View.OnClickListener {

    private var userId = ""
    private var user: User? = null
    private var userData: UserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        if (supportActionBar != null) {
            supportActionBar?.hide()
            window.statusBarColor = Color.parseColor("#3e444b")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE //设置状态栏黑色字体
            }
        }
        userId = intent.getStringExtra("user_id")
        showLoading("加载中...")
        userIcon.postDelayed({
            loadProfile()
        }, 1000)
    }

    private var loadResponse = 0

    private fun loadProfile() {
        DataUtil.getUserData(userId) { userData ->
            if (userData != null) {
                this.userData = userData
                // 关注
                val query2 = BmobQuery<User>();
                var userData2 = UserData()
                userData2.objectId = userData.objectId
                query2.addWhereRelatedTo("attentionList", BmobPointer(userData2))
                query2.findObjects(object : FindListener<User>() {
                    override fun done(p0: MutableList<User>?, p1: BmobException?) {
                        if (p1 == null && p0 != null) {
                            userData2.attention = p0.size
                            userData2.update(object : UpdateListener() {
                                override fun done(p0: BmobException?) {
                                    if (p0 == null) {
                                        loadResponse++
                                        syncProfile()
                                    } else {
                                        showToast("加载失败，请重试")
                                        hideLoading()
                                    }
                                }
                            })
                        } else {
                            showToast("加载失败，请重试")
                            hideLoading()
                        }
                    }
                })


                // 被关注
                val query3 = BmobQuery<User>();
                userData2 = UserData()
                userData2.objectId = userData.objectId
                query3.addWhereRelatedTo("fansList", BmobPointer(userData2))
                query3.findObjects(object : FindListener<User>() {
                    override fun done(p0: MutableList<User>?, p1: BmobException?) {
                        if (p1 == null && p0 != null) {
                            val u = p0.find { it.objectId == BmobUser.getCurrentUser(User::class.java).objectId }
                            followBtn.isSelected = u != null
                            followBtn.text = if (u != null) "关注中" else "关注"
                            userData2.fans = p0.size
                            userData2.update(object : UpdateListener() {
                                override fun done(p0: BmobException?) {
                                    if (p0 == null) {
                                        loadResponse++
                                        syncProfile()
                                    } else {
                                        showToast("加载失败，请重试")
                                        hideLoading()
                                    }
                                }
                            })
                        } else {
                            showToast("加载失败，请重试")
                            hideLoading()
                        }
                    }
                })

                // 获赞
                val query4 = BmobQuery<Post>()
                val user111 = User()
                user111.objectId = userId
                query4.addWhereEqualTo("author", BmobPointer(user111))
                query4.findObjects(object : FindListener<Post>() {
                    override fun done(list: List<Post>?, e: BmobException?) {
                        if (e == null && list != null) {
                            var praise = 0
                            for (post in list) {
                                praise += post.praise
                            }
                            userData2 = UserData()
                            userData2.objectId = userData.objectId
                            userData2.praise = praise
                            userData2.post = list.size
                            userData2.update(object : UpdateListener() {
                                override fun done(e: BmobException?) {
                                    if (e == null) {
                                        loadResponse++
                                        syncProfile()
                                    } else {
                                        showToast("加载失败，请重试")
                                        hideLoading()
                                    }
                                }
                            })
                        } else {
                            showToast("加载失败，请重试")
                            hideLoading()
                        }
                    }
                })
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun syncProfile() {
        if (loadResponse != 3) {
            return
        }
        val query = BmobQuery<User>()
        query.getObject(userId, object : QueryListener<User>() {
            override fun done(user: User?, p1: BmobException?) {
                if (p1 == null && user != null) {
                    this@ProfileActivity.user = user
                    val options = RequestOptions()
                        .placeholder(R.drawable.icon_user_default) //图片加载出来前，显示的图片
                        .fallback(R.drawable.icon_user_default) //url为空的时候,显示的图片
                        .error(R.drawable.icon_user_default) //图片加载失败后，显示的图片
                    Glide.with(this@ProfileActivity).load(user.iconUrl).apply(options).into(userIcon)
                    followBtn.visibility = if (BmobUser.getCurrentUser(User::class.java).objectId == userId) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                    userNameTv.text = user.name
                    if (user.sex != null && user.sex == "女") {
                        userNameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(R.drawable.ic_female), null)
                    } else if (user.sex != null && user.sex == "男") {
                        userNameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(R.drawable.ic_male), null)
                    } else {
                        userNameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    }
                    userNameTv.setTextColor(Constant.userColors[abs(user.objectId.hashCode()) % Constant.userColors.size])
                    if (!user.wantGoCompany.isNullOrBlank()) {
                        if (!user.work.isNullOrBlank()) {
                            userCompanyTv.text = user.wantGoCompany + "_" + user.work
                        } else {
                            userCompanyTv.text = user.wantGoCompany
                        }
                    } else {
                        userCompanyTv.text = "暂未设置所在企业"
                    }
                    if (!user.school.isNullOrBlank()) {
                        userUniversity.text = user.school
                    } else {
                        userUniversity.text = "未知"
                    }
                    if (!user.location.isNullOrBlank()) {
                        userLocationTv.text = "居住地：" + user.location
                    } else {
                        userLocationTv.text = "居住地：保密"
                    }
                    if (!user.introduction.isNullOrBlank()) {
                        userIntroductionTv.text = "个人简介：" + user.introduction
                    } else {
                        userIntroductionTv.text = "个人简介：暂无"
                    }
                    followBtn.setOnClickListener(this@ProfileActivity)
                    val fragment = PostListFragment.newFragment(userId, "")
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.postVG, fragment)
                        .commit()
                    newsVg.setOnClickListener {
                        startActivity(Intent(this@ProfileActivity, PostListActivity::class.java).apply {
                            putExtra("userId", userId)
                            putExtra("title", "最新动态")
                        })
                    }
                    hideLoading()
                }
            }
        })

        DataUtil.getUserData(userId) { userData ->
            if (userData != null) {
                this.userData = userData
                userFollowingBtn.text = "关注 " + userData.attention
                userFollowedBtn.text = "被关注 " + userData.fans
                userLikedBtn.text = "共获赞 " + userData.praise
                userLikedBtn.setOnClickListener {
                    ProfileLikeCountDialog(this@ProfileActivity, userData.praise).show()
                }
                userFollowingBtn.setOnClickListener {
                    startActivity(Intent(this, UserListActivity::class.java).apply {
                        putExtra("title", "关注列表")
                        putExtra("userId", userId)
                    })
                }
                userFollowedBtn.setOnClickListener {
                    startActivity(Intent(this, UserListActivity::class.java).apply {
                        putExtra("title", "粉丝列表")
                        putExtra("userId", userId)
                    })
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            followBtn -> {
                handleFollow()
            }
        }
    }

    private var followLock = false

    private fun handleFollow() {
        if (followLock) {
            return
        }
        followLock = true
        if (followBtn.isSelected) {
            if (userData != null) {
                val nowUserData = UserData()
                nowUserData.objectId = userData?.objectId
                val nowUserRelation = BmobRelation()
                nowUserRelation.remove(BmobUser.getCurrentUser(User::class.java))
                nowUserData.fansList = nowUserRelation
                nowUserData.update(object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            DataUtil.getUserData(BmobUser.getCurrentUser(User::class.java).objectId) { myUD ->
                                if (myUD != null) {
                                    val myRelation = BmobRelation()
                                    val myUserData = UserData()
                                    myUserData.objectId = myUD.objectId
                                    myRelation.remove(User().apply { objectId = userId })
                                    myUserData.attentionList = myRelation
                                    myUserData.update(object : UpdateListener() {
                                        @SuppressLint("SetTextI18n")
                                        override fun done(e: BmobException?) {
                                            if (e == null) {
                                                showToast("取关成功")
                                                followBtn.isSelected = false
                                                followBtn.text = "关注"
                                                this@ProfileActivity.userData!!.fans -= 1
                                                userFollowedBtn.text = "被关注 " + this@ProfileActivity.userData!!.fans
                                                followLock = false
                                            } else {
                                                showToast("取关失败")
                                                followLock = false
                                            }
                                        }
                                    })
                                }
                            }
                        } else {
                            showToast("取关失败")
                            followLock = false
                        }
                    }
                })
            }
        } else {
            if (userData != null) {
                val nowUserData = UserData()
                nowUserData.objectId = userData?.objectId
                val nowRelation = BmobRelation()
                nowRelation.add(BmobUser.getCurrentUser(User::class.java))
                nowUserData.fansList = nowRelation
                nowUserData.update(object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            DataUtil.getUserData(BmobUser.getCurrentUser(User::class.java).objectId) { myUD ->
                                val myRelation = BmobRelation()
                                val myUserData = UserData()
                                myUserData.objectId = myUD.objectId
                                myRelation.add(User().apply { objectId = userId })
                                myUserData.attentionList = myRelation
                                myUserData.update(object : UpdateListener() {
                                    @SuppressLint("SetTextI18n")
                                    override fun done(e: BmobException?) {
                                        if (e == null) {
                                            showToast("关注成功")
                                            followBtn.isSelected = true
                                            followBtn.text = "已关注"
                                            this@ProfileActivity.userData!!.fans += 1
                                            userFollowedBtn.text = "被关注 " + this@ProfileActivity.userData!!.fans
                                            followLock = false
                                        } else {
                                            showToast("关注失败")
                                            followLock = false
                                        }
                                    }
                                })
                            }
                        } else {
                            showToast("关注失败")
                            followLock = false
                        }
                    }
                })
            }
        }
    }
}
