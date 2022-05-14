package com.turtlemint.mymachinetest.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.turtlemint.mymachinetest.R
import com.turtlemint.mymachinetest.data.IssueList
import com.turtlemint.mymachinetest.ui.adapter.IssuesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssuesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var errortv: TextView
    private lateinit var issuesAdapter: IssuesAdapter
    private lateinit var progress: ProgressBar
    private val viewModel: IssueListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView = findViewById(R.id.recycler_vew)
        progress = findViewById(R.id.progress)
        errortv = findViewById(R.id.error)
        recyclerView.visibility= View.GONE
        progress.visibility= View.VISIBLE
        val notificationModelArrayList: ArrayList<IssueList> = ArrayList<IssueList>()
        issuesAdapter = IssuesAdapter(this, notificationModelArrayList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = issuesAdapter
        fetchIssues()
    }

    private fun fetchIssues() {
        viewModel.getData(this)
        viewModel.issuesList.observe(this) {
            if (it.isNotEmpty()) {
                recyclerView.visibility= View.VISIBLE
                errortv.visibility= View.GONE
                viewModel.insertNewDataToDB()
                issuesAdapter.updateList(it)
            }else{
                recyclerView.visibility= View.GONE
                errortv.visibility= View.VISIBLE
            }
            progress.visibility= View.GONE
        }

    }


}