package HackerRank.Training.FunctionalProgramming.Introduction

import java.io.{ByteArrayInputStream, IOException, PrintWriter}
import java.util.InputMismatchException

import scala.annotation.tailrec
import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

/**
  * Copyright (c) 2017 A. Roberto Fischer
  *
  * @author A. Roberto Fischer <a.robertofischer@gmail.com> on 6/1/2017
  */
object EvalEPowX {
  private[this] val INPUT = ""

  //------------------------------------------------------------------------------------------//
  // Solution                                                                
  //------------------------------------------------------------------------------------------//
  private[this] def solve(): Unit = {
    val n = nextInt()
    nextDouble[Vector](n).foreach(x => println(e(x)))

  }

  private[this] def power(n: Double, i: Int): Double = {
    @tailrec
    def _power(n: Double, i: Int, current: Double): Double = {
      if (i == 1) {
        current
      } else {
        _power(n, i - 1, current * n)
      }
    }

    if (i == 0) 1 else _power(n, i, n)
  }

  def e(x: Double): Double = {
    (1 until 10).map(current => power(x, current) / (1 to current).product).sum + 1d
  }

  //------------------------------------------------------------------------------------------//
  // Input-Output                                                                 
  //------------------------------------------------------------------------------------------//
  private[this] var in: java.io.InputStream = _
  private[this] var out: java.io.PrintWriter = _

  private[this] def println(x: Any) = out.println(x)

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
    if (!INPUT.isEmpty) System.out.println(System.currentTimeMillis - s + "ms")
  }

  private[this] def nextDouble[Coll[Double]]
  (n: Int)(implicit cbf: CanBuildFrom[Coll[Double], Double, Coll[Double]]): Coll[Double] = {
    val builder = cbf()
    builder.sizeHint(n)
    for (_ <- 0 until n) {
      builder += nextDouble()
    }
    builder.result()
  }

  private[this] def nextDouble(): Double = nextString().toDouble

  private[this] def nextString(): String = {
    var b = skip
    val sb = new java.lang.StringBuilder
    while (!isSpaceChar(b)) {
      sb.appendCodePoint(b)
      b = readByte()
    }
    sb.toString
  }

  private[this] def nextInt(): Int = {
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

  private[this] val inputBuffer = new Array[Byte](1024)
  var lenBuffer = 0
  var ptrBuffer = 0

  private[this] def readByte(): Int = {
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

  private[this] def isSpaceChar(c: Int) = !(c >= 33 && c <= 126)

  private[this] def skip = {
    var b = 0
    while ( {
      b = readByte()
      b != -1 && isSpaceChar(b)
    }) {}
    b
  }

}