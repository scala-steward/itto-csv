import com.github.gekomad.ittocsv.parser.IttoCSVFormat

class CsvLineTest extends munit.FunSuite:
  test("csvStringToList") {

    given IttoCSVFormat = com.github.gekomad.ittocsv.parser.IttoCSVFormat.default
    import com.github.gekomad.ittocsv.util.StringUtils.*
    {
      val csvString = """1,"foo,bar",y,"2,e,","2ne","a""bc""z""""

      assert(tokenizeCsvLine(csvString) == Some(List("1", "foo,bar", "y", "2,e,", "2ne", "a\"bc\"z")))
    }

    {
      val csvString = "1,foo"

      assert(tokenizeCsvLine(csvString) == Some(List("1", "foo")))
    }

    {
      val csvString = "1,\"foo"

      assert(tokenizeCsvLine(csvString).isEmpty)
    }
  }

end CsvLineTest
