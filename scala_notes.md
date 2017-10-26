# What's good about scala?

Classes - constructors can be parameterised - which are auto-assigned to instance variables, accessible everywhere in the
class.

val parameters???

Scala has no null but has the trait Option[A] instead. If A is present, A is an instance of Some[A], otherwise it's None.
getOrElse method extracts the value of the Option instance.  It can be used to declare a default value to use, if the
function returns None (or any other falsey value??).  e.g. getOrElse "a string to return".
isEmpty also can be called on an Option instance.

Option can also be used with pattern matching and case statements, e.g.
val someValue: Option[Double] = Some(20.0)
val value = someValue match {
  case Some(v) ⇒ v
  case None ⇒ 0.0
}

Option also has callback handler style functions,
map and fold
Option.map allows other data elements to be manipulated and a new map returned, e.g.
val number: Option[Int] = Some(3)         //> number  : Option[Int] = Some(3)
val number2: Option[Int] = None           //> number2  : Option[Int] = None
val mappedNum1 = number.map(_ * 1.5)      //> mappedNum1  : Option[Double] = Some(4.5)
val mappedNum2 = number2.map(_ * 1.5)           //> mappedNum2  : Option[Double] = None

Option.fold (like js reduce) also takes a default param for values that are None. e.g.
val num: Option[Int] = Some(3)                  //> num  : Option[Int] = Some(3)
val noNum: Option[Int] = None             //> noNum  : Option[Int] = None
val result1 = num.fold(0)(_ * 3)          //> result1  : Int = 9
val result2 = noNum.fold(0)(_ * 3)        //> result2  : Int e 
