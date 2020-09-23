package utils

object PropertyReader {

  def getProperty(property: String, defaultValue: Int): Int = {
    Integer.parseInt(System.getProperty(property, String.valueOf(defaultValue)))
  }

}