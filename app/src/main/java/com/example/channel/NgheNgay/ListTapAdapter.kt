package com.example.channel.NgheNgay


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.channel.Profile.MyPodcastFragment
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ListTapAdapter(context: Context, resource: Int, list: List<ListTapData>):
    ArrayAdapter<ListTapData>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.list_tap, parent, false)
        }

        val currentItem = getItem(position)

        val ngayThang = rowView?.findViewById<TextView>(R.id.txtThoiGian)
        val tenTap = rowView?.findViewById<TextView>(R.id.txtTenTap)
        val phut = rowView?.findViewById<TextView>(R.id.txtPhut)
        val imgBtnPlay = rowView?.findViewById<ImageButton>(R.id.imgBtnPlay)
        val imgbtnMore = rowView?.findViewById<ImageButton>(R.id.imgBtnMore)


        val auth: FirebaseAuth
        val storageReference: StorageReference
        val databaseReference: DatabaseReference

        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference("AudioEpisode/")
        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+auth.currentUser?.uid)

        imgBtnPlay?.setOnClickListener {
            EpisodeBottomSheet().show((context as AppCompatActivity).getSupportFragmentManager(), "Episode screen")

            val send_data = Bundle().apply {
                putString("idEpisode", currentItem?._id.toString())
                putString("dateEpisode", currentItem?.date.toString())
                putString("titleEpisode", currentItem?.title.toString())
                putString("descriptEpisode", currentItem?.descript.toString())
                putString("imgEpisode", currentItem?.img.toString())
                Log.d("idEpisode", currentItem?._id.toString())
//                putString("position", position.toString())
            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode", send_data)



            val database = FirebaseDatabase.getInstance()
            val ref = database.reference.child("categories")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (categorySnapshot in dataSnapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                                if(currentItem?._id.toString().equals(episodeSnapshot.key.toString())){





                                    val numListener = episodeSnapshot.child("listener").value.toString().toInt()

                                    val newNumListener = numListener +1

                                    var cateRef = categorySnapshot.key.toString()
                                    Log.d("cateRef",cateRef)
                                    var AlbumRef = albumSnapshot.key.toString()
                                    Log.d("AlbumRef",cateRef)
                                    var episoedeRef = albumSnapshot.child("episodes").ref
                                    val reafRef = "categories" +"/" +cateRef+"/albums/"+AlbumRef+"/episodes/"+ episodeSnapshot.key.toString()
                                    Log.d("RealRef",reafRef)
                                    val myRef = database.getReference(reafRef)
                                    val updates: MutableMap<String, Any> = HashMap()
                                    updates["listener"] = newNumListener.toString()

                                    myRef.updateChildren(updates)






                                }

                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })


        }

        //popup menu cho imgbutton more
        val popupMenu = PopupMenu(rowView?.context, imgbtnMore)

        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSave -> {
                    databaseReference.get().addOnSuccessListener {
                        if (it.exists()){
                            if(it.child("saved").exists()){
                                var found = false
                                for (i in it.child("saved").children){
                                    if(i.value.toString().equals(currentItem?._id
                                            .toString())){
                                        Toast.makeText(context, "Episode has been saved", Toast.LENGTH_SHORT).show()
                                        found = true
                                        break
                                    }
                                }
                                if(!found){
                                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                                    databaseReference.child("saved").push().setValue(currentItem?._id.toString())
                                }
                            }
                            else{
                                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                                databaseReference.child("saved").push().setValue(currentItem?._id.toString())
                            }
                        }
                    }

                    true
                }
                R.id.menuDownload -> {

                    true
                }

                else -> false
            }
        }

        imgbtnMore?.setOnClickListener {

            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)

            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                popupMenu.show()
            }
            true

        }


        ngayThang?.text = currentItem?.date
        tenTap?.text = currentItem?.title
//        phut?.text = currentItem?.phut
        phut?.text = currentItem?.duration




        return rowView!!


    }


}