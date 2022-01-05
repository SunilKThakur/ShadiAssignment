package com.shadiassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shadiassignment.R
import com.shadiassignment.databinding.ItemShadiMatchBinding
import com.shadiassignment.models.ShadiMatchDBModel


class ShadiMatchAdapter() : RecyclerView.Adapter<ShadiMatchAdapter.ShadiMatchHolder>() {
    private var usersList: List<ShadiMatchDBModel>? = null
    private var mContext: Context? = null
    var acceptDeclineClickListener: ((ShadiMatchDBModel) -> Unit)? = null

    // Counstructor for the Class
    constructor(usersList: List<ShadiMatchDBModel>, context: Context?) : this() {
        this.usersList = usersList
        mContext = context
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ShadiMatchHolder {
        val itemShadiMatchBinding =
            ItemShadiMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShadiMatchHolder(itemShadiMatchBinding)
    }

    override fun getItemCount(): Int {
        return usersList?.size ?: 0
    }

    // This method is called when binding the data to the views being created in RecyclerView
    override fun onBindViewHolder(@NonNull holder: ShadiMatchHolder, position: Int) {
        usersList?.let {
            val shadiMatchDBModel: ShadiMatchDBModel = it[position]

            // Set the data to the views here
            holder.apply {
                setUserName(shadiMatchDBModel.name.first)
                setImage(shadiMatchDBModel.picture.thumbnail)
                when (shadiMatchDBModel.isAccepted) {
                    0 -> {
                        setButtonsLayoutVisibility(true)
                    }
                    1 -> {
                        tvMemberAcceptance.text = mContext?.getString(R.string.shadi_member_accepted)
                        mContext?.let { context ->
                            ContextCompat.getColor(
                                context,R.color.green)
                        }?.let { it -> tvMemberAcceptance.setBackgroundColor(it) }
                        setButtonsLayoutVisibility(false)
                    }
                    2 -> {
                        tvMemberAcceptance.text = mContext?.getString(R.string.shadi_member_declined)
                        mContext?.let { context ->
                            ContextCompat.getColor(
                                context,R.color.red)
                        }?.let { it -> tvMemberAcceptance.setBackgroundColor(it) }
                        setButtonsLayoutVisibility(false)
                    }
                }
                btnAccept.setOnClickListener {
                    shadiMatchDBModel.isAccepted=1
                    acceptDeclineClickListener?.invoke(shadiMatchDBModel)
                }
                btnDecline.setOnClickListener {
                    shadiMatchDBModel.isAccepted=2
                    acceptDeclineClickListener?.invoke(shadiMatchDBModel)
                }
            }
        }

    }

    // This is your ViewHolder class that helps to populate data to the view
    inner class ShadiMatchHolder(itemViewBinding: ItemShadiMatchBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        private val tvUserName: TextView
        private val imageAvatar: ImageView
        private val buttonsLayout: LinearLayoutCompat
        val tvMemberAcceptance: TextView
        val btnAccept: Button
        val btnDecline: Button
        fun setUserName(name: String?) {
            tvUserName.text = name
        }

        fun setButtonsLayoutVisibility(isShow: Boolean) {
            if (isShow) {
                tvMemberAcceptance.visibility = View.GONE
                buttonsLayout.visibility = View.VISIBLE
            } else {
                tvMemberAcceptance.visibility = View.VISIBLE
                buttonsLayout.visibility = View.GONE
            }
        }

        fun setImage(image: String) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)



            mContext?.let { Glide.with(it).load(image).apply(options).into(imageAvatar) }
        }

        init {
            tvUserName = itemViewBinding.textViewUserName
            tvMemberAcceptance = itemViewBinding.textViewMemberAccepted
            imageAvatar = itemViewBinding.imageViewAvatar
            buttonsLayout = itemViewBinding.linearButtonsLayout
            btnAccept = itemViewBinding.btnAccept
            btnDecline = itemViewBinding.btnDecline
        }
    }
}