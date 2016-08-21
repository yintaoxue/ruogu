package org.ruogu.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

/**
 * UnitParamGenericUDF
 * 
 * @author xueyintao 2016年6月8日 上午11:31:48
 */
public class UnitParamGenericUDF extends GenericUDF {

	@Override
	public ObjectInspector initialize(ObjectInspector[] paramArrayOfObjectInspector) throws UDFArgumentException {
		return null;
	}

	@Override
	public Object evaluate(DeferredObject[] paramArrayOfDeferredObject) throws HiveException {
		return null;
	}

	@Override
	public String getDisplayString(String[] paramArrayOfString) {
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
