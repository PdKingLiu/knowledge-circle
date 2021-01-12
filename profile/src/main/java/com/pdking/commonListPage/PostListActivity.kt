package com.pdking.commonListPage

import android.os.Bundle
import com.competition.pdking.lib_base.BaseActivity
import com.pdking.profile.R
import kotlinx.android.synthetic.main.activity_post_list.*

class PostListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)
        val id = intent.getStringExtra("userId") ?: ""
        val title = intent.getStringExtra("title") ?: "帖子列表"
        titleTv.text = title
        val fragment = PostListFragment.newFragment(id, title)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.viewGroup, fragment)
            .commit()
    }
}
