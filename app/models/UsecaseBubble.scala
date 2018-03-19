package models

case class UsecaseBubble(desc:String, includes: Set[UsecaseInclude]){
  override def  toString = desc
}
