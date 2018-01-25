import java.util.Date
import person.PersonClass

object Operations extends App {
  val address1: String = "B - 49/ F -1, Ambika Palace"
  val address2: String = "Shalimar Garden, Extension 2"
  val address3: String = "Sahibababd, Ghaziabad, Uttar Pradesh"
  val country: String = "India"
  val name: String = "Sudeep James Tirkey"
  val number: Int = 99
  val person = new PersonClass
  val fullAddress = new person.Address(address1, address2, address3, country)
  val detail = new person.Person(name, Some(number), Some(new Date()), fullAddress)
  person.writeJSON(detail)
  person.readJSON()
}
