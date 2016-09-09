注解						说明
@AssertFalse			验证注解的元素值是false
@AssertTrue				验证注解的元素值是true
@DecimalMax（value=x）	验证注解的元素值小于等于@ DecimalMax指定的value值
@DecimalMin（value=x）	验证注解的元素值小于等于@ DecimalMin指定的value值
@Digits(integer=整数位数, fraction=小数位数)	验证注解的元素值的整数位数和小数位数上限
@Future					验证注解的元素值（日期类型）比当前时间晚
@Max（value=x）			验证注解的元素值小于等于@Max指定的value值
@Min（value=x）			验证注解的元素值大于等于@Min指定的value值
@NotNull				验证注解的元素值不是null
@Null					验证注解的元素值是null
@Past					验证注解的元素值（日期类型）比当前时间早
@Pattern(regex=正则表达式, flag=)	验证注解的元素值与指定的正则表达式匹配
@Size(min=最小值, max=最大值)	验证注解的元素值的在min和max（包含）指定区间之内，如字符长度、集合大小
@Valid					验证关联的对象，如账户对象里有一个订单对象，指定验证订单对象
@NotEmpty				验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
@Range(min=最小值, max=最大值)	验证注解的元素值在最小值和最大值之间
@NotBlank				验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
@Length(min=下限, max=上限)	验证注解的元素值长度在min和max区间内
@Email					验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式