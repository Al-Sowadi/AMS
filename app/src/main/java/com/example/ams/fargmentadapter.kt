package com.abdullah.ams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ams.databinding

class fargmentadapter(private val userList: ArrayList<databinding>) : RecyclerView.Adapter<fargmentadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):fargmentadapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design,parent,false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: fargmentadapter.MyViewHolder,position: Int) {
        val users :databinding=userList[position]
        holder.seminarname.text=users.t
        holder.seminartime.text=users.m
        holder.seminardate.text=users.ti

    }
    override fun getItemCount(): Int {
        return userList.size
    }
    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val seminarname: TextView =itemView.findViewById(R.id.titleCard)
        val seminartime:TextView=itemView.findViewById(R.id.messageCard)
        val seminardate:TextView=itemView.findViewById(R.id.timeCard)
    }
}