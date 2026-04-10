package top.aichezhan.ssyx.result;

import lombok.Data;

@Data
public class Result<T> {

    //状态码
    private Integer code;
    //信息
    private String message;
    //数据
    private T data;

    //构造私有化
    private  Result() {}

    //设置数据，返回对象的方法
    public static<T> Result<T> build(T data,ResultCodeEnum resultCodeEnum) {
        //创建Result对象，设置值，返回对象
        Result<T> result = new Result<T>();
        //判断返回结果中是否需要数据
        if(data!=null) {
            //设置数据到Result对象
            result.setData(data);
        }
        //设置其他值
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());

        return  result;
    }
    /**
     * 构建返回结果（动态指定状态码和错误信息）
     */
    public static <T> Result<T> build(T data, Integer code, String message) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    //成功的方法
    public static <T> Result<T> ok(T data) {return build(data,ResultCodeEnum.SUCCESS);}
    public static <T> Result<T> ok() { return build(null,ResultCodeEnum.SUCCESS); }
    //失败的方法
    public static <T> Result<T> fail(T data) {
        return build(data,ResultCodeEnum.FAIL);
    }
    public static <T> Result<T> fail() {
        return build(null,ResultCodeEnum.FAIL);
    }
}
