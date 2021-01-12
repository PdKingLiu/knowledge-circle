package com.pdking.profile.wight

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.pdking.profile.R
import kotlinx.android.synthetic.main.profile_like_count_dialog.*

class ProfileLikeCountDialog(context: Context, private var likeCount: Int?) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_like_count_dialog)
        profileLikeCountTv.text = "一共获赞${likeCount}次"
        profileLikeCountConfirmTv.setOnClickListener {
            dismiss()
        }
    }
}