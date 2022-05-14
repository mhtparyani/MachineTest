package com.turtlemint.mymachinetest.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turtlemint.mymachinetest.R
import com.turtlemint.mymachinetest.data.CommentsList
import com.turtlemint.mymachinetest.ui.adapter.CommentsAdapter
import com.turtlemint.mymachinetest.util.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView


@AndroidEntryPoint
class CommentsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var titletv: TextView
    private lateinit var username: TextView
    private lateinit var desciption: TextView
    private lateinit var avatar: CircleImageView
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var errortv: TextView
    private lateinit var comments: TextView
    private lateinit var url: String
    private var issueId: Int= 0
    private lateinit var progress: ProgressBar
    private val viewModel: CommentsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        val toolbar: Toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle= intent.extras
        titletv = findViewById(R.id.title)
        username = findViewById(R.id.user_name)
        desciption = findViewById(R.id.description)
        comments = findViewById(R.id.comments)
        avatar = findViewById(R.id.profile_image)
        progress = findViewById(R.id.progress)
        if (bundle!=null){
            url= bundle.getString(AppConstants.COMM_URL,"")
            issueId= bundle.getInt(AppConstants.ISSUE_ID,0)
            desciption.text=bundle.getString(AppConstants.USER_DES, "")
            Glide.with(this).load(bundle.getString(AppConstants.USER_AVATAR, "")).into(avatar)
            username.text=bundle.getString(AppConstants.USER_NAME, "")
            titletv.text=bundle.getString(AppConstants.TITLE, "")
        }
        setRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_vew)
        errortv = findViewById(R.id.error)
        recyclerView.visibility= View.GONE
        progress.visibility= View.VISIBLE
        val commentsList: ArrayList<CommentsList> = ArrayList<CommentsList>()
        commentsAdapter = CommentsAdapter(this, commentsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = commentsAdapter
        recyclerView.isNestedScrollingEnabled= false
        fetchComments()
    }

    private fun fetchComments() {
        viewModel.getData(this,url,issueId)
        viewModel.commentList.observe(this) {
            if (it.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                errortv.visibility = View.GONE
                comments.visibility= View.VISIBLE
                viewModel.insertNewDataToDB(issueId)
                commentsAdapter.updateList(it)
            }else{
                recyclerView.visibility= View.GONE
                errortv.visibility= View.VISIBLE
                comments.visibility= View.GONE
            }
            progress.visibility= View.GONE
        }

    }
}