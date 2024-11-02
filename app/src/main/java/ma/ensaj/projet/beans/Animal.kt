package ma.ensaj.projet.beans

data class Animal(
    val id : Int = ++comp,
    var name : String,
    var type : String,
    var nbr : Int,
    var star : Float,
    var img : String)
{
    companion object {
        private var comp =0
    }
}
