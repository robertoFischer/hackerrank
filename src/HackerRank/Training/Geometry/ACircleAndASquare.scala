package HackerRank.Training.Geometry

import java.io.{ByteArrayInputStream, IOException, PrintWriter}
import java.util.InputMismatchException

import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

/**
  * Copyright (c) 2017 A. Roberto Fischer
  *
  * @author A. Roberto Fischer <a.robertofischer@gmail.com> on 5/24/2017
  */
object ACircleAndASquare {
  private val INPUT = ""

  //------------------------------------------------------------------------------------------//
  // Solution                                                                
  //------------------------------------------------------------------------------------------//
  def solve(): Unit = {
    val w = nextInt()
    val h = nextInt()
    val circleX = nextInt()
    val circleY = nextInt()
    val r = nextInt()
    val a = Canvas(w, h)
    val square = Vector((nextInt(), nextInt()), (nextInt(), nextInt())).sortBy(_._1)
    a.drawCircle((circleX, circleY), r)
    a.drawSquare(square(0), square(1))
    out.println(a)
  }

  case class Pixel(x: Int, y: Int, var value: Boolean = false) {
    override def toString: String = {
      if (value) {
        "#"
      } else {
        "."
      }
    }
  }

  case class Square(a: (Double, Double), b: (Double, Double), c: (Double, Double), d: (Double, Double)) {

    def isInside(point: (Double, Double)): Boolean = {
      val AB = EuclideanVector(a, b)
      val AP = EuclideanVector(a, point)
      val BC = EuclideanVector(b, c)
      val BP = EuclideanVector(b, point)
      val ABdotAP = scalarProduct(AB, AP)
      val ABdotAB = scalarProduct(AB, AB)
      val BCdotBP = scalarProduct(BC, BP)
      val BCdotBC = scalarProduct(BC, BC)
      0 <= ABdotAP && ABdotAP <= ABdotAB && 0 <= BCdotBP && BCdotBP <= BCdotBC
    }
  }

  def scalarProduct(a: EuclideanVector, b: EuclideanVector): Double = {
    a.scalarProduct(b)
  }

  case class EuclideanVector(a: (Double, Double), b: (Double, Double)) {
    val x: Double = b._1 - a._1
    val y: Double = b._2 - a._2

    def scalarProduct(b: EuclideanVector): Double = {
      x * b.x + y * b.y
    }
  }

  case class Canvas(width: Int, height: Int) {
    private[this] val canvas = Array.tabulate[Pixel](width, height) {
      (i, j) => Pixel(i, j)
    }

    def setPixel(x: Int, y: Int): Unit = {
      canvas(x)(y).value = true
    }

    def drawCircle(center: (Int, Int), radius: Int): Unit = {
      for {
        x <- 0 until width
        y <- 0 until height
        if distanceSquared((x, y), center) <= radius * radius
      } yield setPixel(x, y)
    }

    def drawSquare(A: (Int, Int), C: (Int, Int)): Unit = {
      val B = ((A._1 + C._1 + C._2 - A._2) / 2.0, (A._2 + C._2 + A._1 - C._1) / 2.0)
      val D = ((A._1 + C._1 + A._2 - C._2) / 2.0, (A._2 + C._2 + C._1 - A._1) / 2.0)
      val square = Square((A._1.toDouble, A._2.toDouble), B, (C._1.toDouble, C._2.toDouble), D)
      for {
        x <- 0 until width
        y <- 0 until height
        if square.isInside((x.toDouble, y.toDouble))
      } yield setPixel(x, y)
    }

    def distanceSquared(a: (Int, Int), b: (Int, Int)): Int = {
      (a._1 - b._1) * (a._1 - b._1) + (a._2 - b._2) * (a._2 - b._2)
    }


    override def toString: String = {
      val result = StringBuilder.newBuilder
      for (y <- 0 until height) {
        for (x <- 0 until width) {
          result.append(canvas(x)(y))
        }
        result.append("\n")
      }
      result.toString
    }
  }

  //------------------------------------------------------------------------------------------//
  // Input-Output
  //------------------------------------------------------------------------------------------//
  var in: java.io.InputStream = _
  var out: java.io.PrintWriter = _

  @throws[Exception]
  def main(args: Array[String]): Unit = {
    run()
  }

  @throws[Exception]
  def run(): Unit = {
    in = if (INPUT.isEmpty) System.in else new ByteArrayInputStream(INPUT.getBytes)
    out = new PrintWriter(System.out)

    val s = System.currentTimeMillis
    solve()
    out.flush()
    if (!INPUT.isEmpty) printCustom(System.currentTimeMillis - s + "ms")
  }

  private def nextSeq[T, Coll[_]](reader: => Seq[T], n: Int)
                                 (implicit cbf: CanBuildFrom[Coll[T], T, Coll[T]]): Coll[T] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder ++= reader
    }
    builder.result()
  }

  private def next[T, Coll[_]](reader: => T, n: Int)
                              (implicit cbf: CanBuildFrom[Coll[T], T, Coll[T]]): Coll[T] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder += reader
    }
    builder.result()
  }

  private def nextWithIndex[T, Coll[_]](reader: => T, n: Int)
                                       (implicit cbf: CanBuildFrom[Coll[(T, Int)], (T, Int), Coll[(T, Int)]]): Coll[(T, Int)] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (i <- 0 until n) {
      builder += ((reader, i))
    }
    builder.result()
  }

  private def nextDouble[Coll[Double]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[Double], Double, Coll[Double]]): Coll[Double] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder += nextDouble()
    }
    builder.result()
  }

  private def nextDoubleWithIndex[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[(Double, Int)], (Double, Int), Coll[(Double, Int)]]): Coll[(Double, Int)] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (i <- 0 until n) {
      builder += ((nextDouble(), i))
    }
    builder.result()
  }

  private def nextChar[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[Char], Char, Coll[Char]]): Coll[Char] = {
    val builder = cbf()
    builder.sizeHint(n)
    var b = skip
    var p = 0
    while (p < n && !isSpaceChar(b)) {
      builder += b.toChar
      p += 1
      b = readByte()
    }
    builder.result()
  }

  private def nextCharWithIndex[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[(Char, Int)], (Char, Int), Coll[(Char, Int)]]): Coll[(Char, Int)] = {
    val builder = cbf()
    builder.sizeHint(n)
    var b = skip
    var p = 0
    while (p < n && !isSpaceChar(b)) {
      builder += ((b.toChar, p))
      p += 1
      b = readByte()
    }
    builder.result()
  }

  private def nextInt[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[Int], Int, Coll[Int]]): Coll[Int] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder += nextInt()
    }
    builder.result()
  }

  private def nextIntWithIndex[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[(Int, Int)], (Int, Int), Coll[(Int, Int)]]): Coll[(Int, Int)] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (i <- 0 until n) {
      builder += ((nextInt(), i))
    }
    builder.result()
  }

  private def nextLong[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[Long], Long, Coll[Long]]): Coll[Long] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder += nextLong()
    }
    builder.result()
  }

  private def nextLongWithIndex[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[(Long, Int)], (Long, Int), Coll[(Long, Int)]]): Coll[(Long, Int)] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (i <- 0 until n) {
      builder += ((nextLong(), i))
    }
    builder.result()
  }

  private def nextString[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[String], String, Coll[String]]): Coll[String] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder += nextString()
    }
    builder.result()
  }

  private def nextStringWithIndex[Coll[_]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[(String, Int)], (String, Int), Coll[(String, Int)]]): Coll[(String, Int)] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (i <- 0 until n) {
      builder += ((nextString(), i))
    }
    builder.result()
  }

  private def nextMultiLine(n: Int, m: Int): Array[Array[Char]] = {
    val map = new Array[Array[Char]](n)
    var i = 0
    while (i < n) {
      map(i) = nextChar[Array](m)
      i += 1
    }
    map
  }

  private def nextDouble(): Double = nextString().toDouble

  private def nextChar(): Char = skip.toChar

  private def nextString(): String = {
    var b = skip
    val sb = new java.lang.StringBuilder
    while (!isSpaceChar(b)) {
      sb.appendCodePoint(b)
      b = readByte()
    }
    sb.toString
  }

  private def nextInt(): Int = {
    var num = 0
    var b = 0
    var minus = false
    while ( {
      b = readByte()
      b != -1 && !((b >= '0' && b <= '9') || b == '-')
    }) {}
    if (b == '-') {
      minus = true
      b = readByte()
    }
    while (true) {
      if (b >= '0' && b <= '9') {
        num = num * 10 + (b - '0')
      } else {
        if (minus) return -num else return num
      }
      b = readByte()
    }
    throw new IOException("Read Int")
  }

  private def nextLong(): Long = {
    var num = 0L
    var b = 0
    var minus = false
    while ( {
      b = readByte()
      b != -1 && !((b >= '0' && b <= '9') || b == '-')
    }) {}
    if (b == '-') {
      minus = true
      b = readByte()
    }
    while (true) {
      if (b >= '0' && b <= '9') {
        num = num * 10 + (b - '0')
      } else {
        if (minus) return -num else return num
      }
      b = readByte()
    }
    throw new IOException("Read Long")
  }

  private val inputBuffer = new Array[Byte](1024)
  var lenBuffer = 0
  var ptrBuffer = 0

  private def readByte(): Int = {
    if (lenBuffer == -1) throw new InputMismatchException
    if (ptrBuffer >= lenBuffer) {
      ptrBuffer = 0
      try {
        lenBuffer = in.read(inputBuffer)
      } catch {
        case _: IOException =>
          throw new InputMismatchException
      }
      if (lenBuffer <= 0) return -1
    }
    inputBuffer({
      ptrBuffer += 1
      ptrBuffer - 1
    })
  }

  private def isSpaceChar(c: Int) = !(c >= 33 && c <= 126)

  private def skip = {
    var b = 0
    while ( {
      b = readByte()
      b != -1 && isSpaceChar(b)
    }) {}
    b
  }

  private def printCustom(o: AnyRef*): Unit = {
    System.out.println(java.util.Arrays.deepToString(o.toArray)
    )
  }
}
