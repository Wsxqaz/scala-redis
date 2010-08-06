package com.redis

trait NodeOperations { self: Redis =>

  // SAVE
  // save the DB on disk now.
  def save: Boolean = {
    send("SAVE")
    asBoolean
  }

  // BGSAVE
  // save the DB in the background.
  def bgsave: Boolean = {
    send("BGSAVE")
    asBoolean
  }

  // LASTSAVE
  // return the UNIX TIME of the last DB SAVE executed with success.
  def lastsave: Option[Int] = {
    send("LASTSAVE")
    as[Int]
  }
  
  // SHUTDOWN
  // Stop all the clients, save the DB, then quit the server.
  def shutdown: Boolean = {
    send("SHUTDOWN")
    asBoolean
  }

  // BGREWRITEAOF
  def bgrewriteaof: Boolean = {
    send("BGREWRITEAOF")
    asBoolean
  }

  // INFO
  // the info command returns different information and statistics about the server.
  def info = {
    send("INFO")
    as[String]
  }
  
  // MONITOR
  // is a debugging command that outputs the whole sequence of commands received by the Redis server.
  def monitor: Boolean = {
    send("MONITOR")
    asBoolean
  }
  
  // SLAVEOF
  // The SLAVEOF command can change the replication settings of a slave on the fly.
  def slaveof(options: Any): Boolean = options match {
    case (h: String, p: Int) => {
      send("SLAVEOF", h, String.valueOf(p))
      asBoolean
    }
    case _ => setAsMaster
  }
  
  @deprecated("use slaveof") def slaveOf(options: Any): Boolean = slaveof(options)

  private def setAsMaster: Boolean = {
    send("SLAVEOF NO ONE")
    asBoolean
  }
}
