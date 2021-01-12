package com.pdking.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FetchUserInfoListener
import cn.bmob.v3.listener.UpdateListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.competition.pdking.lib_base.BaseActivity
import com.competition.pdking.lib_base.com.competition.pdking.bean.PictureUploadBean
import com.competition.pdking.lib_base.com.competition.pdking.bean.User
import com.competition.pdking.lib_common_resourse.toast.ToastUtils
import com.competition.pdking.lib_common_resourse.utils.FileUtils
import com.google.gson.Gson
import com.pdking.profile.wight.InputDialog
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_profile_edit.*
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.*

class ProfileEditActivity : BaseActivity() {

    private val GO_ALBUM = 999
    private var isLoadSuccess = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        showLoading("加载中···")
        userIconIv.postDelayed({
            loadProfile()
        }, 1000)
    }

    private fun loadProfile() {
        BmobUser.fetchUserInfo(object : FetchUserInfoListener<BmobUser>() {
            override fun done(user: BmobUser, e: BmobException?) {
                if (e == null) {
                    hideLoading()
                    val user2: User = BmobUser.getCurrentUser(User::class.java)
                    syncProfile(user2)
                } else {
                    hideLoading()
                    isLoadSuccess = false
                    showToast("加载失败")
                }
            }
        })
    }

    private fun syncProfile(user: User) {
        hideLoading()
        val options = RequestOptions()
            .placeholder(R.drawable.icon_user_default)//图片加载出来前，显示的图片
            .fallback(R.drawable.icon_user_default) //url为空的时候,显示的图片
            .error(R.drawable.icon_user_default);//图片加载失败后，显示的图片
        Glide.with(this).load(user.iconUrl).apply(options).into(userIconIv)
        userNameTv.text = user.name
        userSexTv.text = user.sex
        userIntroductionTv.text = user.introduction
        userLocationTv.text = user.location
        userGraduationTv.text = user.graduation
        userEducationTv.text = user.education
        userUniversityTv.text = user.school
        userWantGoTv.text = user.wantGoCompany
        userWorkTv.text = user.work
    }

    fun onClick(view: View) {
        if (!isLoadSuccess) {
            showToast("加载失败，请重新进入页面进行尝试")
            return
        }

        val user = User()
        user.objectId = BmobUser.getCurrentUser(User::class.java).objectId
        when (view) {
            userIconVG -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, GO_ALBUM)
            }
            userNameVG -> {
                InputDialog(this, "昵称", "请输入昵称") {
                    user.name = it
                    updateUser(user)
                }.show()
            }
            userSexVG -> {
                InputDialog(this, "性别", "请输入性别（男/女）") {
                    if (it != "男" && it != "女") {
                        showToast("性别有误，请重新设置")
                    } else {
                        user.sex = it
                        updateUser(user)
                    }
                }.show()
            }
            userIntroductionVG -> {
                InputDialog(this, "个人简介", "请输入个人简介") {
                    user.introduction = it
                    updateUser(user)
                }.show()
            }
            userLocationVG -> {
                InputDialog(this, "居住地", "请输入居住地") {
                    user.location = it
                    updateUser(user)
                }.show()
            }
            userGraduationVG -> {
                InputDialog(this, "毕业年份", "请输入毕业年份") {
                    user.graduation = it
                    updateUser(user)
                }.show()
            }
            userEducationVG -> {
                InputDialog(this, "学历", "请输入学历") {
                    user.education = it
                    updateUser(user)
                }.show()
            }
            userUniversityVG -> {
                InputDialog(this, "学校", "请输入学校") {
                    user.school = it
                    updateUser(user)
                }.show()
            }
            userWantGOVG -> {
                InputDialog(this, "公司", "请输入你所在的公司") {
                    user.wantGoCompany = it
                    updateUser(user)
                }.show()
            }
            userWorkVG -> {
                InputDialog(this, "岗位", "请输入岗位") {
                    user.work = it
                    updateUser(user)
                }.show()
            }
        }
    }

    private fun updateUser(user: User) {
        user.update(object : UpdateListener() {
            override fun done(p0: BmobException?) {
                if (p0 == null) {
                    setResult(Activity.RESULT_OK, Intent().putExtra("is_modify", 1))
                    showToast("修改成功")
                    loadProfile()
                } else {
                    showToast("修改失败")
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GO_ALBUM -> if (resultCode == Activity.RESULT_OK && data != null) {
                val file: File? = FileUtils().uriToFile(this,
                    Objects.requireNonNull(data.data))
                if (file == null) {
                    ToastUtils.showToast(this, "未知错误")
                    return
                }
                doUpLoadFile(file)
            }
            2 -> if (resultCode == 10) {
                finish()
            }
        }
    }

    private fun doUpLoadFile(fileSource: File) {
        showLoading("上传中···")
        Handler().postDelayed({
            var file: File? = null
            try {
                file = Compressor(this).compressToFile(fileSource)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (file == null) {
                showToast("上传失败")
                hideLoading()
                return@postDelayed
            }
            val client = OkHttpClient()
            val body = MultipartBody.Builder()
                .addFormDataPart("file", file.name, RequestBody.create(MediaType.parse(
                    "image/jpeg"), file))
                .build()
            val request = Request.Builder()
                .post(body)
                .url("http://204.44.94.217:3003/upload")
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    showToast("上传失败")
                    hideLoading()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    var bean: PictureUploadBean? = null
                    val s = response.body()!!.string()
                    var isOk = true
                    try {
                        bean = Gson().fromJson(s, PictureUploadBean::class.java)
                    } catch (e: Exception) {
                        isOk = false
                    }
                    if (isOk) {
                        if (bean?.url?.isNotEmpty() == true) {
                            val user = User()
                            user.objectId = BmobUser.getCurrentUser(User::class.java).objectId
                            user.iconUrl = bean.url
                            user.update(object : UpdateListener() {
                                override fun done(p0: BmobException?) {
                                    if (p0 == null) {
                                        showToast("修改成功")
                                        hideLoading()
                                        Glide.with(this@ProfileEditActivity).load(bean.url).into(userIconIv)
                                    } else {
                                        showToast("修改失败")
                                        hideLoading()
                                    }
                                }
                            })
                        }
                    } else {
                        showToast("上传失败")
                        hideLoading()
                    }
                }
            })
        }, 1000)
    }
}
