package com.abdullah.ams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class userAdapter(private val userList: ArrayList<userDataAdpter>) : RecyclerView.Adapter<userAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_all_user,parent,false)
        return userAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:userAdapter.MyViewHolder, position: Int) {
        val users1 :userDataAdpter=userList[position]
        holder.seminarname1.text=users1.u1
        holder.seminartime1.text=users1.u2
        holder.seminardate1.text=users1.u3
        holder.seminarname11.text=users1.u4
        holder.seminartime11.text=users1.u5
        holder.seminardate11.text=users1.u6

    }
    override fun getItemCount(): Int {
        return userList.size
    }
    public class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val seminarname1: TextView =itemView.findViewById(R.id.userdataile1)
        val seminartime1: TextView =itemView.findViewById(R.id.userdataile2)
        val seminardate1: TextView =itemView.findViewById(R.id.userdataile3)
        val seminarname11: TextView =itemView.findViewById(R.id.userdataile4)
        val seminartime11: TextView =itemView.findViewById(R.id.userdataile5)
        val seminardate11: TextView =itemView.findViewById(R.id.userdataile6)
    }
}