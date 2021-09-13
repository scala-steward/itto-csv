import com.github.gekomad.ittocsv.parser.StringToCsvField
import org.junit.Test
import com.github.gekomad.ittocsv.core.ToCsv
import com.github.gekomad.ittocsv.core.ToCsv.*
import com.github.gekomad.ittocsv.core.Types.RegexValidator
import com.github.gekomad.ittocsv.core.FromCsv.Decoder
import scala.util.matching.Regex
import com.github.gekomad.ittocsv.core.ToCsv.given
import com.github.gekomad.ittocsv.core.FromCsv.*

class TreeTest {
  @Test def encode_decodeTree_Int(): Unit = {

    object OTree {

      //thanks to amitayh https://gist.github.com/amitayh/373f512c50222e15550869e2ff539b25
      final case class Tree[A](value: A, left: Option[Tree[A]] = None, right: Option[Tree[A]] = None)

      object Serializer {
        val pattern: Regex = """^(\d+)\((.*)\)$""".r
        val treeOpen: Char = '('
        val treeClose: Char = ')'
        val separator: Char = ','
        val separatorLength: Int = 1

        def serialize[A](nodeOption: Option[Tree[A]]): String = nodeOption match {
          case Some(Tree(value, left, right)) =>
            val leftStr = serialize(left)
            val rightStr = serialize(right)
            s"$value$treeOpen$leftStr$separator$rightStr$treeClose"

          case None => ""
        }

        def deserialize[A](str: String, f: String => A): Option[Tree[A]] = str match {
          case pattern(value, inner) =>
            val (left, right) = splitInner(inner)
            Some(Tree(f(value), deserialize(left, f), deserialize(right, f)))
          case _ => None
        }

        def splitInner(inner: String): (String, String) = {
          var balance = 0
          val left = inner.takeWhile {
            case `treeOpen`                  => balance += 1; true
            case `treeClose`                 => balance -= 1; true
            case `separator` if balance == 0 => false
            case _                           => true
          }

          val right = inner.drop(left.length + separatorLength)

          (left, right)
        }
      }

    }
    import OTree.*
    import OTree.Serializer.*

    import com.github.gekomad.ittocsv.parser.IttoCSVFormat

    given IttoCSVFormat = IttoCSVFormat.default

    final case class Foo(v: String, a: Tree[Int])

    //encode
    import com.github.gekomad.ittocsv.core.ToCsv.*
    given FieldEncoder[Tree[Int]] = customFieldEncoder[Tree[Int]](x => serialize(Some(x)))

    val tree: Tree[Int] = Tree(1, Some(Tree(2, Some(Tree(3)))), Some(Tree(4, Some(Tree(5)), Some(Tree(6)))))

    val serialized: String = toCsv(Foo("abc", tree))

    assert(serialized == "abc,\"1(2(3(,),),4(5(,),6(,)))\"")

    //decode
    given Decoder[String, Tree[Int]] = str => {
      deserialize(str, _.toInt) match {
        case None    => Left(List(s"Not a Node[Short] $str"))
        case Some(a) => Right(a)
      }
    }

    assert(fromCsv[Foo](serialized) == List(Right(Foo("abc", tree))))
  }
}
