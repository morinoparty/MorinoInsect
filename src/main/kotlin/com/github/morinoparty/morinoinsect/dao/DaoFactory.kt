package com.github.morinoparty.morinoinsect.dao

import com.github.morinoparty.morinoinsect.MorinoInsect
import com.github.morinoparty.morinoinsect.dao.yaml.YamlRecordDao

/**
 * Created by elsiff on 2019-01-18.
 */
object DaoFactory {
    private lateinit var morinoInsect: MorinoInsect

    val records: RecordDao
        get() = YamlRecordDao(morinoInsect, morinoInsect.insectTypeTable)

    fun init(morinoInsect: MorinoInsect) {
        this.morinoInsect = morinoInsect
    }
}
