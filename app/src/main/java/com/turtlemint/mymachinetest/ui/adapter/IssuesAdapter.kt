package com.turtlemint.mymachinetest.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turtlemint.mymachinetest.R
import com.turtlemint.mymachinetest.data.IssueList
import com.turtlemint.mymachinetest.ui.CommentsActivity
import com.turtlemint.mymachinetest.util.AppConstants
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat

class IssuesAdapter(context: Context, data: List<IssueList>) : RecyclerView.Adapter<IssuesAdapter.IssuesHolder>() {

    private var dataList: List<IssueList> = data
    var context= context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.issues_list_cell_layout, parent, false)
        return IssuesHolder(view)
    }

    override fun onBindViewHolder(holder: IssuesHolder, position: Int) {
        val data= dataList[position]
        holder.title?.text= data.title
        holder.descriptionTxtView?.text= data.description
        holder.userNameTxtView?.text= data.userName
        holder.userAvatarImgView?.let { Glide.with(context).load(data.avatar).into(it) }
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = simpleDateFormat.parse(data.updatedAt)
        val simpleDateFormat2 = SimpleDateFormat("MM-dd-yyyy")
        holder.date?.text= simpleDateFormat2.format(date)
        holder.itemView.setOnClickListener {
            val bundle= Bundle()
            bundle.putString(AppConstants.COMM_URL, data.commentsUrl)
            bundle.putInt(AppConstants.ISSUE_ID, data.id)
            bundle.putString(AppConstants.USER_DES, data.description)
            bundle.putString(AppConstants.USER_AVATAR, data.avatar)
            bundle.putString(AppConstants.USER_NAME, data.userName)
            bundle.putString(AppConstants.TITLE, data.title)
            val intent= Intent(context, CommentsActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data: List<IssueList>){
        if (data.isNotEmpty()) {
            this.dataList = data
            notifyDataSetChanged()
        }

    }
    override fun getItemCount(): Int {
        return  dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class IssuesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userAvatarImgView: CircleImageView?= null
        var userNameTxtView: TextView?= null
        var descriptionTxtView: TextView?= null
        var title: TextView?= null
        var date: TextView?= null

        init {
            userAvatarImgView = itemView.findViewById(R.id.profile_image)
            userNameTxtView = itemView.findViewById(R.id.user_name)
            descriptionTxtView = itemView.findViewById(R.id.description)
            title = itemView.findViewById(R.id.title)
            date = itemView.findViewById(R.id.date)
        }
    }
}