package models

case class UsecaseInclude(desc:String) {
  override def toString: String = desc
}

object UsecaseInclude {
  def hasInclude(usecaseDiagram: UsecaseDiagram): Boolean = {
    var hasInclude: Boolean = false
    usecaseDiagram.actorsAndUsecases.values.foreach(set => {
      for (elem <- set) {
        if (elem.includes.size > 0) {
          hasInclude = true;
        }
      }
    })
    hasInclude
  }
}
