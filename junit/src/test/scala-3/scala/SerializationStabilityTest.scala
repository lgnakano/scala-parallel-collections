/*
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala

import javax.xml.bind.DatatypeConverter._
import java.nio.file.{ Path, Paths, Files }
import org.junit.Test

// This test is self-modifying when run as follows:
//
//    junit/testOnly scala.SerializationStabilityTest -- -Doverwrite.source=src/test/scala-3/scala/SerializationStabilityTest.scala
//
// Use this to re-establish a baseline for serialization compatibility.

// based on run/t8549.scala partest
object SerializationStability extends App with SerializationStabilityBase {

  def check[T <: AnyRef](instance: => T)(prevResult: String, f: T => AnyRef = (x: T) => x): Unit = {
    val result = serialize(instance)
    overwrite match {
      case Some(f) =>
        val lineNumberOfLiteralString = Thread.currentThread.getStackTrace.apply(2).getLineNumber
        patch(f, lineNumberOfLiteralString, prevResult, result)
      case None =>
        checkRoundTrip(instance)(f)
        assert(f(deserialize(prevResult).asInstanceOf[T]) == f(instance), s"$instance != f(deserialize(prevResult))")
        assert(prevResult == result, s"instance = $instance : ${instance.getClass}\n serialization unstable: ${prevResult}\n   found: ${result}")
    }
  }

  // Generated on 20201015-13:11:40 with Scala version 2.13.3)
  overwrite.foreach(updateComment)

  // check(new collection.concurrent.TrieMap[Any, Any]())( "rO0ABXNyACNzY2FsYS5jb2xsZWN0aW9uLmNvbmN1cnJlbnQuVHJpZU1hcKckxpgOIYHPAwAETAALZXF1YWxpdHlvYmp0ABJMc2NhbGEvbWF0aC9FcXVpdjtMAApoYXNoaW5nb2JqdAAcTHNjYWxhL3V0aWwvaGFzaGluZy9IYXNoaW5nO0wABHJvb3R0ABJMamF2YS9sYW5nL09iamVjdDtMAAtyb290dXBkYXRlcnQAOUxqYXZhL3V0aWwvY29uY3VycmVudC9hdG9taWMvQXRvbWljUmVmZXJlbmNlRmllbGRVcGRhdGVyO3hwc3IAMnNjYWxhLmNvbGxlY3Rpb24uY29uY3VycmVudC5UcmllTWFwJE1hbmdsZWRIYXNoaW5nhTBoJQ/mgb0CAAB4cHNyABhzY2FsYS5tYXRoLkVxdWl2JCRhbm9uJDLBbyx4dy/qGwIAAHhwc3IANHNjYWxhLmNvbGxlY3Rpb24uY29uY3VycmVudC5UcmllTWFwU2VyaWFsaXphdGlvbkVuZCSbjdgbbGCt2gIAAHhweA==")
  // not sure why this one needs stable serialization.

  import collection.parallel
  check(parallel.immutable.ParHashMap(1 -> 2))( "rO0ABXNyAC5zY2FsYS5jb2xsZWN0aW9uLnBhcmFsbGVsLmltbXV0YWJsZS5QYXJIYXNoTWFwAAAAAAAAAAMCAARKAAkwYml0bWFwJDFMAA1TY2FuTGVhZiRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5MZWFmJDtMAA1TY2FuTm9kZSRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5Ob2RlJDtMAAR0cmlldAAnTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL09sZEhhc2hNYXA7eHAAAAAAAAAAAHBwc3IAMXNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLk9sZEhhc2hNYXAkT2xkSGFzaE1hcDEu846aGc5HjAIABEkABGhhc2hMAANrZXl0ABJMamF2YS9sYW5nL09iamVjdDtMAAJrdnQADkxzY2FsYS9UdXBsZTI7TAAFdmFsdWVxAH4ABnhyACVzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5PbGRIYXNoTWFwGzyZ5OayDwECAAB4cP+DzudzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNyAAxzY2FsYS5UdXBsZTIB+93NIuc0egIAAkwAAl8xcQB+AAZMAAJfMnEAfgAGeHBxAH4ADHNxAH4ACgAAAAJxAH4ADw==")
  check(parallel.immutable.ParHashSet(1, 2, 3))( "rO0ABXNyAC5zY2FsYS5jb2xsZWN0aW9uLnBhcmFsbGVsLmltbXV0YWJsZS5QYXJIYXNoU2V0AAAAAAAAAAECAARKAAkwYml0bWFwJDFMAA1TY2FuTGVhZiRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5MZWFmJDtMAA1TY2FuTm9kZSRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5Ob2RlJDtMAAR0cmlldAAnTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL09sZEhhc2hTZXQ7eHAAAAAAAAAAAHBwc3IAMXNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLk9sZEhhc2hTZXQkSGFzaFRyaWVTZXTiTcUmrrCZogIAA0kABmJpdG1hcEkABXNpemUwWwAFZWxlbXN0AChbTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL09sZEhhc2hTZXQ7eHIAJXNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLk9sZEhhc2hTZXRY25CS1TvmFgIAAHhwABBAgAAAAAN1cgAoW0xzY2FsYS5jb2xsZWN0aW9uLmltbXV0YWJsZS5PbGRIYXNoU2V0OwgA+jL9wEgOAgAAeHAAAAADc3IAMXNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLk9sZEhhc2hTZXQkT2xkSGFzaFNldDEdQIAs6u3ODgIAAkkABGhhc2hMAANrZXl0ABJMamF2YS9sYW5nL09iamVjdDt4cgA0c2NhbGEuY29sbGVjdGlvbi5pbW11dGFibGUuT2xkSGFzaFNldCRMZWFmT2xkSGFzaFNldF2Et+1jWqbdAgAAeHEAfgAH/4PO53NyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3EAfgAL/4OsznNxAH4ADwAAAAJzcQB+AAv/g4rUc3EAfgAPAAAAAw==")
  // TODO SI-8576 Uninitialized field under -Xcheckinit
  // check(new parallel.immutable.ParRange(new Range(0, 1, 2)))( "rO0ABXNyACxzY2FsYS5jb2xsZWN0aW9uLnBhcmFsbGVsLmltbXV0YWJsZS5QYXJSYW5nZQAAAAAAAAABAgAETAAXUGFyUmFuZ2VJdGVyYXRvciRtb2R1bGV0AEBMc2NhbGEvY29sbGVjdGlvbi9wYXJhbGxlbC9pbW11dGFibGUvUGFyUmFuZ2UkUGFyUmFuZ2VJdGVyYXRvciQ7TAAPU2NhbkxlYWYkbW9kdWxldAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5MZWFmJDtMAA9TY2FuTm9kZSRtb2R1bGV0ADVMc2NhbGEvY29sbGVjdGlvbi9wYXJhbGxlbC9QYXJJdGVyYWJsZUxpa2UkU2Nhbk5vZGUkO0wABXJhbmdldAAiTHNjYWxhL2NvbGxlY3Rpb24vaW1tdXRhYmxlL1JhbmdlO3hwcHBwc3IAIHNjYWxhLmNvbGxlY3Rpb24uaW1tdXRhYmxlLlJhbmdlabujVKsVMg0CAAdJAANlbmRaAAdpc0VtcHR5SQALbGFzdEVsZW1lbnRJABBudW1SYW5nZUVsZW1lbnRzSQAFc3RhcnRJAARzdGVwSQAPdGVybWluYWxFbGVtZW50eHAAAAABAAAAAAAAAAABAAAAAAAAAAIAAAAC")
  // TODO SI-8576 unstable under -Xcheckinit
  // check(parallel.mutable.ParArray(1, 2, 3))( "rO0ABXNyACpzY2FsYS5jb2xsZWN0aW9uLnBhcmFsbGVsLm11dGFibGUuUGFyQXJyYXkAAAAAAAAAAQMABEwAF1BhckFycmF5SXRlcmF0b3IkbW9kdWxldAA+THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvbXV0YWJsZS9QYXJBcnJheSRQYXJBcnJheUl0ZXJhdG9yJDtMAA9TY2FuTGVhZiRtb2R1bGV0ADVMc2NhbGEvY29sbGVjdGlvbi9wYXJhbGxlbC9QYXJJdGVyYWJsZUxpa2UkU2NhbkxlYWYkO0wAD1NjYW5Ob2RlJG1vZHVsZXQANUxzY2FsYS9jb2xsZWN0aW9uL3BhcmFsbGVsL1Bhckl0ZXJhYmxlTGlrZSRTY2FuTm9kZSQ7TAAIYXJyYXlzZXF0ACNMc2NhbGEvY29sbGVjdGlvbi9tdXRhYmxlL0FycmF5U2VxO3hwcHBwc3IAMXNjYWxhLmNvbGxlY3Rpb24ucGFyYWxsZWwubXV0YWJsZS5FeHBvc2VkQXJyYXlTZXGx2OTefAodSQIAAkkABmxlbmd0aFsABWFycmF5dAATW0xqYXZhL2xhbmcvT2JqZWN0O3hyACFzY2FsYS5jb2xsZWN0aW9uLm11dGFibGUuQXJyYXlTZXEVPD3SKEkOcwIAAkkABmxlbmd0aFsABWFycmF5cQB+AAd4cAAAAAN1cgATW0xqYXZhLmxhbmcuT2JqZWN0O5DOWJ8QcylsAgAAeHAAAAADcHBwAAAAA3VxAH4ACgAAABBzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNxAH4ADQAAAAJzcQB+AA0AAAADcHBwcHBwcHBwcHBwcHg=")
  check(parallel.mutable.ParHashMap(1 -> 2))( "rO0ABXNyACxzY2FsYS5jb2xsZWN0aW9uLnBhcmFsbGVsLm11dGFibGUuUGFySGFzaE1hcAAAAAAAAAADAwAJSgAJMGJpdG1hcCQxSQALX2xvYWRGYWN0b3JJAAlzZWVkdmFsdWVJAAl0YWJsZVNpemVJAAl0aHJlc2hvbGRMAA1TY2FuTGVhZiRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5MZWFmJDtMAA1TY2FuTm9kZSRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5Ob2RlJDtbAAdzaXplbWFwdAACW0lbAAV0YWJsZXQAJVtMc2NhbGEvY29sbGVjdGlvbi9tdXRhYmxlL0hhc2hFbnRyeTt4cHcNAAAC7gAAAAEAAAAEAXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3EAfgAGAAAAAng=")
  check(parallel.mutable.ParHashSet(1, 2, 3))( "rO0ABXNyACxzY2FsYS5jb2xsZWN0aW9uLnBhcmFsbGVsLm11dGFibGUuUGFySGFzaFNldAAAAAAAAAABAwAJSgAJMGJpdG1hcCQxSQALX2xvYWRGYWN0b3JJAAlzZWVkdmFsdWVJAAl0YWJsZVNpemVJAAl0aHJlc2hvbGRMAA1TY2FuTGVhZiRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5MZWFmJDtMAA1TY2FuTm9kZSRsenkxdAA1THNjYWxhL2NvbGxlY3Rpb24vcGFyYWxsZWwvUGFySXRlcmFibGVMaWtlJFNjYW5Ob2RlJDtbAAdzaXplbWFwdAACW0lbAAV0YWJsZXQAE1tMamF2YS9sYW5nL09iamVjdDt4cHcNAAABwgAAAAMAAAAbAXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAABc3EAfgAGAAAAAnNxAH4ABgAAAAN4")
}

class SerializationStabilityTest {
  @Test
  def testAll: Unit = SerializationStability.main(new Array[String](0))
}
