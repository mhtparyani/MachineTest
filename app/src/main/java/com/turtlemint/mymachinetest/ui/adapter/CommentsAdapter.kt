package com.turtlemint.mymachinetest.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turtlemint.mymachinetest.R
import com.turtlemint.mymachinetest.data.CommentsList
import com.turtlemint.mymachinetest.data.IssueList
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat

class CommentsAdapter(context: Context, data: List<CommentsList>) : RecyclerView.Adapter<CommentsAdapter.CommentsHolder>() {

    private var dataList: List<CommentsList> = data
    var context= context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.comments_list_cell_layout, parent, false)
        return CommentsHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsHolder, position: Int) {
        val data= dataList[position]
        holder.descriptionTxtView?.text= data.description
        holder.userNameTxtView?.text= data.userName
        holder.userAvatarImgView?.let { Glide.with(context).load(data.avatar).into(it) }
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = simpleDateFormat.parse(data.updatedAt)
        val simpleDateFormat2 = SimpleDateFormat("MM-dd-yyyy")
        holder.date?.text= simpleDateFormat2.format(date)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data: List<CommentsList>){
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

    class CommentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userAvatarImgView: CircleImageView?= null
        var userNameTxtView: TextView?= null
        var descriptionTxtView: TextView?= null
        var date: TextView?= null

        init {
            userAvatarImgView = itemView.findViewById(R.id.profile_image)
            userNameTxtView = itemView.findViewById(R.id.user_name)
            descriptionTxtView = itemView.findViewById(R.id.description)
            date = itemView.findViewById(R.id.date)
        }
    }
}