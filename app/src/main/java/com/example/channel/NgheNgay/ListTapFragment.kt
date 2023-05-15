package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.channel.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.concurrent.TimeUnit

class ListTapFragment : Fragment() {
    private lateinit var ivBack4: ImageView

    private lateinit var listView: ListView
    private lateinit var listTapAdapter: ListTapAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_tap, container, false)
        ivBack4 = view.findViewById(R.id.ivBack4)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = mutableListOf<ListTapData>()
        val tenKenh = view.findViewById<TextView>(R.id.txtTenKenh)

        parentFragmentManager.setFragmentResultListener("send_ref", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@ListTapFragment)
            val taskDanhmuc = result.getString("ref")
            val albumRef = FirebaseDatabase.getInstance().getReference(taskDanhmuc.toString())
            val episodesRef = albumRef.child("episodes")
            val uniqueItems = ArrayList<ListTapData>()
            val set = HashSet<ListTapData>()
            val storage = Firebase.storage
            val idAlbum = albumRef.key.toString()
            val channelName = result.getString("ChannelName")
            tenKenh.setText(channelName)

            ivBack4?.setOnClickListener {
                val send_data = Bundle().apply {
                    putString("idAlbum", idAlbum)
                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)
                parentFragmentManager.popBackStack()
                //nhớ phải add fragment này vào backStack thì mới dùng được
                //https://www.youtube.com/watch?v=b9a3-gZ9CGc


            }
            episodesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (episodeSnapshot in snapshot.children) {
                        val epTitle = episodeSnapshot.child("title").value.toString()
                        val epdes = episodeSnapshot.child("descript").value.toString()
                        val date = episodeSnapshot.child("date").value.toString()
                        val img = episodeSnapshot.child("img").value.toString()
                        val _id = episodeSnapshot.key.toString()

                        Log.d("soluongeepisode",list.size.toString())
                        val numListEpisode = list.size

                        val storageRef = storage.reference.child("AudioEpisode/$_id")



                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            val mediaPlayer = MediaPlayer()
                            mediaPlayer.setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                            )
                            mediaPlayer.setDataSource(uri.toString())
                            mediaPlayer.prepare()

                            mediaPlayer.setOnPreparedListener {


                                val duration =
                                    mediaPlayer.duration // thời lượng của tệp âm thanh (đơn vị là mili giây)
                                Log.d("duration",duration.toString())
                                val minutes = TimeUnit.MILLISECONDS.toMinutes(duration.toLong()).toInt().toString() +" phút "

                                Log.d("minutes",minutes.toString())
                                list.add(ListTapData(_id, date, epTitle, epdes, img, minutes))
                                listTapAdapter = ListTapAdapter(requireContext(), R.layout.list_tap, list.toList())
                                listView = view.findViewById(R.id.lvTap)
                                listView.adapter = listTapAdapter
                            }
                        }




                    }





                }
               // val numList = list.size

                override fun onCancelled(error: DatabaseError) {

                }
            })
            Log.d("list",list.toString())

        }
    }
}