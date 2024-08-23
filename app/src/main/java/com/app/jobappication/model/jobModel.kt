package com.app.jobappication.model
import com.google.gson.annotations.SerializedName

data class JobModel (

  @SerializedName("count"    ) var count    : Long?               = null,
  @SerializedName("next"     ) var next     : String?            = null,
  @SerializedName("previous" ) var previous : String?            = null,
  @SerializedName("results"  ) var results  : ArrayList<Results> = arrayListOf()

)

data class Results (
  @SerializedName("id"                    ) var id                  : String?           = null,
  @SerializedName("role"                  ) var role                : String?           = null,
  @SerializedName("company_name"          ) var companyName         : String?           = null,
  @SerializedName("company_num_employees" ) var companyNumEmployees : String?           = null,
  @SerializedName("employment_type"       ) var employmentType      : String?           = null,
  @SerializedName("location"              ) var location            : String?           = null,
  @SerializedName("remote"                ) var remote              : Boolean?          = null,
  @SerializedName("logo"                  ) var logo                : String?           = null,
  @SerializedName("url"                   ) var url                 : String?           = null,
  @SerializedName("text"                  ) var text                : String?           = null,
  @SerializedName("date_posted"           ) var datePosted          : String?           = null,
  @SerializedName("keywords"              ) var keywords            : ArrayList<String> = arrayListOf(),
  @SerializedName("source"                ) var source              : String?           = null

)
