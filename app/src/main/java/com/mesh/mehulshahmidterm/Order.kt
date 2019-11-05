package com.mesh.mehulshahmidterm

class Order {
    var OrderId: String? = null
    var FName: String? = null
    var LName: String? = null
    var SQuantity: String? = null
    var STypes: String? = null
    var SChoices: String? = null





    // required empty constructor - not needed to add, but needed to read

    constructor() {

    }


    // 1st overload w/all properties passed in


    constructor(
        OrderId: String?,
        FName: String?,
        LName: String?,
        SQuantity: String?,
        STypes: String?,
        SChoices: String?
    ) {
        this.OrderId = OrderId
        this.FName = FName
        this.LName = LName
        this.SQuantity = SQuantity
        this.STypes = STypes
        this.SChoices = SChoices
    }

}