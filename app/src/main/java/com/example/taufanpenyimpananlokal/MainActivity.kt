package com.example.taufanpenyimpananlokal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taufanpenyimpananlokal.databinding.ActivityMainBinding
import com.example.taufanpenyimpananlokal.model.InternalFileRepository
import com.example.taufanpenyimpananlokal.model.Note
import com.example.taufanpenyimpananlokal.model.NoteRepository

class MainActivity : AppCompatActivity() {

    private val repo: NoteRepository by lazy { InternalFileRepository(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*button btn write berfungsi untuk menuliskan dan
        * menambahkan file berserta isi nya */
        binding.btnWrite.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    repo.addNote(
                        Note(
                            binding.editFileName.text.toString(),
                            binding.editTeksCatatan.text.toString()
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, "File Write Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
        /* (btnwrite) Jika kolom text editfilename kosong . akan dapat menambahkan file
        * jika tidak maka akan ada pop-up file gagal di tulis */



        /*Tombol btn read berfungsi untuk memanggil dan menampilkan file yang sudah di tambahkan
        * cara memanggilnya adalah dengan mengisikan nama file yang sudah di tambahkan
        * lalu klik tombol baca .  jika tidak berhasil maka akan menampilkan pop-up file gagal di baca*/
        binding.btnRead.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    val note = repo.getNote(binding.editFileName.text.toString())
                    binding.editTeksCatatan.setText(note.noteText)
                } catch (e: Exception) {
                    Toast.makeText(this, "File Read Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }



        /*tombol btn delete berfungsi untuk menghapus file yang sudah di tambahkan
        * dengan cara menuliskan nama file lalu klik tombol delete jika berhasil maka akan
        * menampilkan pop-up berhasil di hapus namun jka gagal maka akan menampilkan pop-up
        * file tidak di temukan */
        binding.btnDelete.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    if (repo.deleteNote(binding.editFileName.text.toString())) {
                        Toast.makeText(this, "File Deleted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "File Could Not Be Deleted", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "File Delete Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
    }
}