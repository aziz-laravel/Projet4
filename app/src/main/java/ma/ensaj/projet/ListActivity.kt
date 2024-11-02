package ma.ensaj.projet

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ma.ensaj.projet.adapter.AnimalAdapter
import ma.ensaj.projet.beans.Animal
import ma.ensaj.projet.service.AnimalService


class ListActivity : AppCompatActivity() {

    private lateinit var animals: List<Animal>
    private lateinit var recyclerview: RecyclerView
    private var animalAdapter: AnimalAdapter? = null
    private lateinit var service: AnimalService

    override fun onPause() {
        super.onPause()
        // this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "AZI-ZOO"
        toolbar.setTitleTextAppearance(this, R.style.MyToolbarTitleText)

        animals = ArrayList()
        service = AnimalService.getInstance()

        init()
        animalAdapter = AnimalAdapter(this, service.findAll())

        recyclerview = findViewById(R.id.recycler)
        recyclerview.adapter = animalAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation)
    }

    private fun init() {
        service.create(Animal(
            name = "Lion",
            img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9gg6fq8rAKIeKQKcR-h-Y-XAfesKlN_iQ4Q&s",
            star = 5f,
            type = "Mammifère",
            nbr = 3
        ))
        service.create(Animal(
            name = "Perroquet",
            img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRCvCZ7f0l134Lng0nDEyWeZ2goZPIlCRAtw&s",
            star = 3.5f,
            type = "Oiseau",
            nbr = 7
        ))
        service.create(Animal(
            name = "Tigre",
            img = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Siberischer_tiger_de_edit02.jpg/640px-Siberischer_tiger_de_edit02.jpg",
            star = 4f,
            type = "Mammifère",
            nbr = 5
        ))
        service.create(Animal(
            name = "Gorille",
            img = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Gorilla_gorilla_gorilla8.jpg/1200px-Gorilla_gorilla_gorilla8.jpg",
            star = 1f,
            type = "Mammifère",
            nbr = 10
        ))
        service.create(Animal(
            name = "Kangourou",
            img = "https://media.gqmagazine.fr/photos/5e9eebe75d2f1100086a5af3/16:9/w_2560%2Cc_limit/n-kangourou.jpg",
            star = 2f,
            type = "Mammifère",
            nbr = 3
        ))
        service.create(Animal(
            name = "Zèbre",
            img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQv1L-lA85EOYp1V3zxAIkTxXEwqETTfCp3Q&s",
            star = 3.5f,
            type = "Mammifère",
            nbr = 4
        ))
        service.create(Animal(
            name = "Paon",
            img = "https://st.depositphotos.com/1962533/1872/i/450/depositphotos_18724447-stock-photo-peacock-with-fanned-tail.jpg",
            star = 1.5f,
            type = "Piseau",
            nbr = 13
        ))
        service.create(Animal(
            name = "Dauphin",
            img = "https://thumbs.dreamstime.com/b/dauphin-7672006.jpg",
            star = 4.5f,
            type = "Mammifère",
            nbr = 3
        ))

        service.create(Animal(
            name = "Giraffe",
            img = "https://cdn.artphotolimited.com/images/58cacb4500dcd4000b3b67c3/300x300/tour-de-controle-de-la-savane.jpg",
            star = 2.5f,
            type = "Mammifère",
            nbr = 9
        ))


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                animalAdapter?.filter?.filter(newText)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.share) {
            showPopupMenu(findViewById(R.id.share))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_share) {
                val txt = "Animals"
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                    .from(this@ListActivity)
                    .setType(mimeType)
                    .setChooserTitle("Animals")
                    .setText(txt)
                    .startChooser()
                true
            } else {
                false
            }
        }

        popup.show() // Affiche le PopupMenu
    }
}