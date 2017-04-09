package com.allen.remeberpassword

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.allen.remeberpassword.model.UserInfo

/**
 * Created by hasee on 2017/4/3.
 */
class UserInfoAdapter(context: Context, var infos: List<UserInfo>) : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {
    constructor(context: Context) : this(context, ArrayList())

    var inflater: LayoutInflater? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder?, position: Int) {
        with(infos[position]) {
            holder?.titleText?.text = title
            holder?.accountText?.text = account
            holder?.passwordText?.text = password
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserInfoViewHolder {
        return UserInfoViewHolder(inflater!!.inflate(R.layout.user_info_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return infos.size
    }

    class UserInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleText: TextView? = null
        var accountText: TextView? = null
        var passwordText: TextView? = null

        init {
            titleText = itemView.findViewById(R.id.title_text) as TextView
            accountText = itemView.findViewById(R.id.account_text) as TextView
            passwordText = itemView.findViewById(R.id.password_text) as TextView
        }
    }

    fun addData(userInfoList: List<UserInfo>) {
        this.infos = userInfoList
        notifyDataSetChanged()
    }
}
