import java.io.File
import java.io.PrintWriter
import java.util.Date
import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.read
import scala.io.Source

class Person {

  final val UID = 404

  def writeJSON(person: Person): Unit = {
    implicit val formats: DefaultFormats = DefaultFormats
    val log = Logger.getLogger(this.getClass)
    val jsonString = org.json4s.jackson.Serialization.write(person)
    try {
      val inputToFile = new PrintWriter(new File("Data.json"))
      inputToFile.write(jsonString)
      log.info("\nInput recorded to file: Data.json")
      inputToFile.close()
    }
    catch {
      case notFound: Exception => log.info(notFound.getCause)
    }
  }

  def readJSON(): Unit = {
    implicit val formats: DefaultFormats = DefaultFormats
    val log = Logger.getLogger(this.getClass)
    try {
      val bufferedSource = Source.fromFile(new File("Data.json")).mkString
      val person: Person = read[Person](bufferedSource)
      log.info(s"\nDetails of Person\n")
      log.info(s"------------------\n")
      log.info(s"Name        : ${person.name}\n")
      log.info(s"Day         : ${person.day}\n")
      log.info(s"Lucky Number: ${person.luckyNumber}\n")
      log.info(s"Address     : ${person.address.line1}\n")
      log.info(s"            : ${person.address.line2}\n")
      log.info(s"            : ${person.address.line3}\n")
      log.info(s"Country     : ${person.address.country}\n")
    }
    catch {
      case except: Exception => log.info(except.getMessage)
    }
  }

  class Address(val line1: String, val line2: String, val line3: String, val country: String)

  @SerialVersionUID(UID)
  class Person(val name: String, @transient val luckyNumber: Option[Int], @transient val day: Option[Date], val address: Address)
}

object Person extends App {
  val address1: String = "B - 49/ F -1, Ambika Palace"
  val address2: String = "Shalimar Garden, Extension 2"
  val address3: String = "Sahibababd, Ghaziabad, Uttar Pradesh"
  val country: String = "India"
  val name: String = "Sudeep James Tirkey"
  val number: Int = 99
  val person = new Person
  val fullAddress = new person.Address(address1, address2, address3, country)
  val detail = new person.Person(name, Some(number), Some(new Date()), fullAddress)
  person.writeJSON(detail)
  person.readJSON()
}
