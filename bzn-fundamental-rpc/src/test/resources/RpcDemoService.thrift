// 定义包名
namespace java com.bzn.rpc.demo

// 定义Person类
struct Person {
	1: i32 id,
	2: string name,
	3: i64 birthday,
	4: byte sex,
	5: list<string> computers
}

// 定义服务接口类
service PersonService {
	
	i32 add(1:Person person);
	
	i32 remove(1:i32 id);
	
	Person findOne(1:i32 id);
	
	list<Person> findAll();
}