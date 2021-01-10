package com.lianda.githubfinder.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lianda.githubfinder.R
import com.lianda.githubfinder.domain.model.User
import com.lianda.githubfinder.utils.extentions.loadImage
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(
    private val context:Context,
    private val data:MutableList<User>
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOAD_MORE = 1
    }

    var page = 1
    var totalPage = 1
    private val limitShimmerLoading = 4

    var isLoadMoreLoading = false

    lateinit var onLoadMoreListener: OnLoadMoreListener

    private var scrollListener: RecyclerView.OnScrollListener? = null

    var layoutManager: LinearLayoutManager? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
        } else {
            LoadMoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!isLoadMoreLoading) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOAD_MORE
        }
    }

    override fun getItemCount(): Int {
     return  if (isLoadMoreLoading){
         data.size + limitShimmerLoading
     }  else{
         data.size
     }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is UserViewHolder -> holder.bind(data[position])
        }
    }

    var recyclerView: RecyclerView? = null
        set(recyclerView) {
            scrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    layoutManager?.let{layoutManager ->
                        val totalItemCount = layoutManager.itemCount
                        val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                        val isLastPosition = totalItemCount.minus(1) == lastVisibleItem
                        if (!isLoadMoreLoading && isLastPosition && page < totalPage) {
                            onLoadMoreListener.onLoadMore()
                            isLoadMoreLoading = true
                        }
                    }
                }
            }

            recyclerView!!.addOnScrollListener(scrollListener as RecyclerView.OnScrollListener)
            field = recyclerView
        }

    fun removeScrollListener() {
        scrollListener?.let {
            recyclerView?.removeOnScrollListener(it)
        }
    }

    fun notifyAddOrUpdateChanged(newDatas: List<User>) {
        newDatas.forEach { data ->
            if (!this.data.contains(data)) {
                this.data.add(data)
            }
            notifyDataSetChanged()
        }
    }

    fun setLoadMoreProgress(isProgress: Boolean) {
        isLoadMoreLoading = isProgress
        notifyDataSetChanged()
    }


    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

    inner class UserViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(user:User){
            with(itemView){
                imgUser.loadImage(user.avatar,pbImage)
                tvName.text = user.name
            }
        }
    }

    inner class LoadMoreViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {}

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}