package com.hapmate.newssample.ui.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hapmate.newssample.R
import com.hapmate.newssample.config.Config
import com.hapmate.newssample.data.model.articles.Articles
import kotlinx.android.synthetic.main.article_row.view.*
import java.util.*

class ArticlesListAdapter(
    private val articles: ArrayList<Articles>
) : RecyclerView.Adapter<ArticlesListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Articles) {

            Glide.with(itemView.newsImage.context)
                .load(article.urlToImage)
                .into(itemView.newsImage)

            itemView.newsAuthor.text = article.author
            itemView.newsTitle.text = article.title
            val date: Date = Config.dateFormat.parse(article.publishedAt) ?: Date()
            Config.displayDateFormat.timeZone = TimeZone.getTimeZone("IST")
            itemView.newsPublishedAt.text = Config.displayDateFormat.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_row, parent,
                false
            )
        )

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(articles[position])

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<Articles>) {
        articles.addAll(list)
        notifyDataSetChanged()
    }
}