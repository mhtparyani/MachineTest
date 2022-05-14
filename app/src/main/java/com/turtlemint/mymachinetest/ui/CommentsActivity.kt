package com.turtlemint.mymachinetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turtlemint.mymachinetest.R
import com.turtlemint.mymachinetest.data.CommentsList
import com.turtlemint.mymachinetest.data.IssueList
import com.turtlemint.mymachinetest.ui.adapter.CommentsAdapter
import com.turtlemint.mymachinetest.ui.adapter.IssuesAdapter
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
    private val viewModel: CommentsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        val bundle= intent.extras
        titletv = findViewById(R.id.title)
        username = findViewById(R.id.user_name)
        desciption = findViewById(R.id.description)
        comments = findViewById(R.id.comments)
        avatar = findViewById(R.id.profile_image)
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

    private fun setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_vew)
        errortv = findViewById(R.id.error)
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
        }

    }
}