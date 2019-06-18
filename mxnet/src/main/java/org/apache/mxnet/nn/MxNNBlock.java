package org.apache.mxnet.nn;

import com.amazon.ai.Block;
import com.amazon.ai.ndarray.NDArray;
import com.amazon.ai.ndarray.NDList;
import com.amazon.ai.util.PairList;
import java.util.Map;
import org.apache.mxnet.engine.MxNDArray;
import org.apache.mxnet.engine.MxNDFactory;
import org.apache.mxnet.jna.FunctionInfo;
import org.apache.mxnet.jna.JnaUtils;

public abstract class MxNNBlock implements Block {

    private static final Map<String, FunctionInfo> OPS = JnaUtils.getNdArrayFunctions();

    protected String opName;

    @Override
    public NDList forward(NDList inputs, PairList<String, String> params) {
        NDArray[] inputArray = inputs.toArray();
        MxNDArray[] output =
                OPS.get(opName)
                        .invoke(
                                (MxNDFactory) inputs.get(0).getFactory(),
                                (MxNDArray[]) inputArray,
                                params);
        return new NDList(output);
    }

    @Override
    public void backward() {}

    @Override
    public byte[] getEncoded() {
        return new byte[0];
    }
}