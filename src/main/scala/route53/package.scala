package com.pongr.bangarang

import com.amazonaws.services.route53._
import com.amazonaws.services.route53.model._
import com.amazonaws.services.route53.model.ChangeAction._
import com.amazonaws.services.route53.model.RRType._
import scala.collection.JavaConversions._

package object route53 {

  /** Returns a new ChangeResourceRecordSetsRequest created with the specified parameters.
    * @param zoneId the ID of the Route53 Zone to modify.
    * @param name name of the resource record set, this is usually a subdomain of the zone, like sub.domain.com.
    * @param value the resource record value, for an A record this is an IP address.
    * @param action 
    * @param ttl 
    * @param rrType
    * @param setIdentifier
    * @param weight 
    */
  def newChangeResourceRecordSetsRequest(
    zoneId: String, 
    name: String, 
    value: String,
    action: ChangeAction = CREATE,
    ttl: Long = 60,
    rrType: RRType = A,
    setIdentifier: Option[String] = None,
    weight: Option[Long] = None
    //AliasTarget: Option[AliasTarget]
  ): ChangeResourceRecordSetsRequest = {
    var resourceRecordSet = new ResourceRecordSet(name, rrType).withTTL(ttl).withResourceRecords(new ResourceRecord(value))
    for (id <- setIdentifier) resourceRecordSet = resourceRecordSet.withSetIdentifier(id)
    for (w <- weight) resourceRecordSet = resourceRecordSet.withWeight(w)
    new ChangeResourceRecordSetsRequest(
      zoneId, 
      new ChangeBatch(
        Seq(
          new Change(
            action, 
            resourceRecordSet
          )
        )
      )
    )
  }

  /** Returns a new ListResourceRecordSetsRequest created with the specified parameters. */
  def newListResourceRecordSetsRequest(
    zoneId: String,
    name: String,
    rrType: RRType = A,
    maxItems: String = "1"
  ): ListResourceRecordSetsRequest = 
    new ListResourceRecordSetsRequest(zoneId).withStartRecordName(name).withStartRecordType(rrType).withMaxItems(maxItems)

  /** name, type, ttl, value */
  def listResourceRecordSet(
    r53: AmazonRoute53, 
    zoneId: String, 
    name: String,
    rrType: RRType = A
  ): Option[(String, RRType, Long, String)] = 
    r53.listResourceRecordSets(newListResourceRecordSetsRequest(zoneId, name, rrType)).getResourceRecordSets.filter(s => s.getName == name && RRType.fromValue(s.getType) == rrType).headOption.flatMap { set => 
      set.getResourceRecords.headOption.map { record => 
        (set.getName, RRType.fromValue(set.getType), set.getTTL, record.getValue)
      }
    }

  /** Deletes the old record, if one exists, and then creates the new record. */
  def replace(
    r53: AmazonRoute53, 
    zoneId: String,
    name: String,
    newValue: String,
    newTtl: Long = 60,
    oldRrType: RRType = A,
    newRrType: RRType = A
  ): ChangeResourceRecordSetsResult = {
    for ((oldName, oldType, oldTtl, oldValue) <- listResourceRecordSet(r53, zoneId, name, oldRrType)) {
      r53.changeResourceRecordSets(newChangeResourceRecordSetsRequest(zoneId, oldName, oldValue, rrType = oldType, ttl = oldTtl, action = DELETE))
    }
    r53.changeResourceRecordSets(newChangeResourceRecordSetsRequest(zoneId, name, newValue, rrType = newRrType, ttl = newTtl))
  }

}
