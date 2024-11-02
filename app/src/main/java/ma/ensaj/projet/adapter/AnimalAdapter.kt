package ma.ensaj.projet.adapter


import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ma.ensaj.projet.R
import ma.ensaj.projet.beans.Animal
import ma.ensaj.projet.service.AnimalService


class AnimalAdapter(
    private val context: Context,
    private var animals: List<Animal>
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>(), Filterable {

    private val animalFilter = ArrayList(animals)
    private val mFilter = NewFilter(this)
    private val TAG = "AnimalAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return AnimalViewHolder(view).apply {
            itemView.setOnClickListener {
                showEditDialog(this)
            }
        }
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalFilter[position]
        Log.d(TAG, "onBindView call! $position")

        holder.name.text = animal.name.uppercase()
        holder.type.text = animal.type.uppercase()
        holder.nbr.text = animal.nbr.toString()
        holder.star.rating = animal.star
        holder.id.text = animal.id.toString()

        Glide.with(context)
            .load(animal.img)
            .centerCrop()
            .into(holder.img)
    }

    override fun getItemCount(): Int = animalFilter.size

    override fun getFilter(): Filter = mFilter

    private fun showEditDialog(holder: AnimalViewHolder) {
        val popup = LayoutInflater.from(context).inflate(R.layout.animal_edit_item, null, false)
        val img = popup.findViewById<ImageView>(R.id.img)
        val bar = popup.findViewById<RatingBar>(R.id.ratingBar)
        val id = popup.findViewById<TextView>(R.id.id)

        val bitmap = (holder.img.drawable as BitmapDrawable).bitmap
        img.setImageBitmap(bitmap)
        bar.rating = holder.star.rating
        id.text = holder.id.text.toString()

        AlertDialog.Builder(context)
            .setView(popup)
            .setPositiveButton("Valider") { _: DialogInterface, _: Int ->
                val animalId = id.text.toString().toInt()
                val animal = AnimalService.getInstance().findById(animalId)?.apply {
                    this.star = bar.rating
                }
                if (animal != null) {
                    AnimalService.getInstance().update(animal)
                }
                notifyItemChanged(holder.adapterPosition)
            }
            .setNegativeButton("Annuler", null)
            .create()
            .show()
    }

    inner class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id : TextView = itemView.findViewById(R.id.id)
        val name : TextView = itemView.findViewById(R.id.name)
        val type : TextView = itemView.findViewById(R.id.type)
        val nbr : TextView = itemView.findViewById(R.id.nbr)
        val star : RatingBar = itemView.findViewById(R.id.ratingbar)
        val img : ImageView = itemView.findViewById(R.id.circle)

    }

    inner class NewFilter(private val adapter: RecyclerView.Adapter<*>) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredAnimals = ArrayList<Animal>()
            if (constraint.isNullOrEmpty()) {
                filteredAnimals.addAll(animals)
            } else {
                val filterPattern = constraint.toString().lowercase().trim()
                filteredAnimals.addAll(animals.filter { it.name.lowercase().startsWith(filterPattern) })
            }
            return FilterResults().apply {
                values = filteredAnimals
                count = filteredAnimals.size
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            animalFilter.clear()
            animalFilter.addAll(results?.values as List<Animal>)
            adapter.notifyDataSetChanged()
        }
    }
}