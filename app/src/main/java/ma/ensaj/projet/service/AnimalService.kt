package ma.ensaj.projet.service

import ma.ensaj.projet.beans.Animal
import ma.ensaj.projet.dao.IDao

class AnimalService private constructor() : IDao<Animal> {

    private val animals : MutableList<Animal> = mutableListOf()

    companion object{
        private var instance : AnimalService? = null

        fun getInstance(): AnimalService {
            if(instance ==null)
            {
                instance = AnimalService();
            }
            return instance!!
        }
    }

    override fun create(o: Animal) : Boolean{
        return animals.add(o)
    }

    override fun delete(o: Animal): Boolean{
        return animals.remove(o)
    }

    override fun update(o: Animal): Boolean{
        for(a in animals){
            if(a.id == o.id){
                a.name = o.name
                a.img = o.img
                a.nbr = o.nbr
                a.star = o.star
                a.type = o.type
                return true
            }
        }
        return false
    }

    override fun findById(id: Int) : Animal? {
        for(a in animals){
            if(a.id == id){
                return a
            }
        }
        return null
    }

    override fun findAll(): List<Animal>{
        return animals
    }
}