import java.io.{File, PrintWriter}
import java.util.Date
import org.apache.log4j.Logger
import org.json4s._
import org.json4s.jackson.Serialization.read
import scala.io.Source
import scala.util.Random


class PersonClass {

  class Address(val line1: String, val line2: String, line3: String, country: String)

  class Person(val name: String, @transient val luckyNumber: Int, @transient val day: Date, val phoneNumber: String, val address: Address)

  def writeToJSON(person: Person): Unit = {
    implicit val formats: DefaultFormats = DefaultFormats
    val log = Logger.getLogger(this.getClass)
    val jsonString = org.json4s.jackson.Serialization.writePretty(person)
    try {
      val inputToFile = new PrintWriter(new File("Data.json"))
      inputToFile.write(jsonString)
      log.info("\nInput has been recorded to file: Data.json")
      inputToFile.close()
    }
    catch {
      case notFound: Exception => log.info(notFound.getMessage)
    }
  }

  def readFromJSON(): Unit = {
    implicit val formats: DefaultFormats = DefaultFormats
    val log = Logger.getLogger(this.getClass)
    try {
      val bufferedSource = Source.fromFile(new File("Data.json")).mkString
      val person: Person = read[Person](bufferedSource)
      log.info(s"Details of Person \n")
      log.info(s"\nName        : ${person.name}\n")
      log.info(s"Day         : ${person.day}\n")
      log.info(s"Lucky Number: ${person.luckyNumber}\n")
      log.info(s"Phone Number      : ${person.phoneNumber}\n")
      log.info(s"Address   : ${person.address.line1}\n")
      log.info(s"\t: ${person.address.line2}\n")
      log.info(s"\t: ${person.address.line3}\n")
      log.info(s"Country     : ${person.address.country}\n")
    } catch {
      case except: Exception => log.info(except.getMessage)
    }
  }


}

object PersonClass extends App {
  val address1: String = "B - 49/ F -1, Ambika Palace"
  val address2: String = "Shalimar Garden, Extension 2"
  val address3: String = "Sahibababd, Ghaziabad, Uttar Pradesh"
  val country: String = "India"
  val name: String = "Sudeep James Tirkey"
  val number: Int = Random.nextInt()
  val phone: String = "9810622717"
  val person = new PersonClass
  val fullAddress = new person.Address(address1, address2, address3, country)
  val detail = new person.Person(name, number, new Date(), phone, fullAddress)
  person.writeToJSON(detail)
  person.readFromJSON()
}
