package com.example.channel.NgheNgay




import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.channel.R

//class DanhMucAdapter(private val items: List<DanhMuc>) :
//    RecyclerView.Adapter<ItemViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_danh_muc, parent, false)
//
//        return ItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item = items[position]
//        holder.text1.text = item.name
//        holder.image.setImageResource(item.image)
//    }
//
//    override fun getItemCount() = items.size
//}
class DanhMucAdapter(private val data: ArrayList<categoryData>,val context: Context) : RecyclerView.Adapter<DanhMucAdapter.ViewHolder>() {
    var onItemClick: ((categoryData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_danh_muc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:categoryData = data[position]
        Glide.with(context).load(item.image)
            .into(holder.imageView)
        //holder.imageView.setImageResource(item.image)
        holder.textView.text = item.name

    }

    override fun getItemCount() = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivDanhMuc)
        val textView: TextView = itemView.findViewById(R.id.tvTenDanhMuc)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])
            }
        }
    }
}


// old code


//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.channel.R
//import com.bumptech.glide.Glide
//
////class DanhMucAdapter(private val items: List<DanhMuc>) :
////    RecyclerView.Adapter<ItemViewHolder>() {
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
////        val view = LayoutInflater.from(parent.context)
////            .inflate(R.layout.item_danh_muc, parent, false)
////
////        return ItemViewHolder(view)
////    }
////
////    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
////        val item = items[position]
////        holder.text1.text = item.name
////        holder.image.setImageResource(item.image)
////    }
////
////    override fun getItemCount() = items.size
////}
//class DanhMucAdapter(private val data: ArrayList<categoryData>) : RecyclerView.Adapter<DanhMucAdapter.ViewHolder>() {
//    var onItemClick: ((categoryData) -> Unit)? = null
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.ivDanhMuc)
//        val textView: TextView = itemView.findViewById(R.id.tvTenDanhMuc)
//
//        init {
//            itemView.setOnClickListener {
//                onItemClick?.invoke(data[adapterPosition])
//            }
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val context = parent.context
//        val inflater = LayoutInflater.from(context)
//        var danhMucView: View? = null
//        danhMucView = inflater.inflate(R.layout.item_danh_muc, parent, false)
//        return ViewHolder(danhMucView)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item: categoryData = data[position]
//        holder.imageView.setImageResource(item.image)
//        Glide.with(context).load(item.image)
//            .into(holder.imageView)
//        holder.textView.text = item.name
//    }
//
//    override fun getItemCount() = data.size
//
//
//}