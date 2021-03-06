package com.demo.test.swaggertest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.test.swaggertest.domain.JsonResult;
import com.demo.test.swaggertest.domain.User;
import com.demo.test.swaggertest.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author gwl
 * @data 2018年5月11日
 */
@RestController
@RequestMapping("/users") //通过这里配置使下面的映射都在/users下，
public class UserController {
	
	@Autowired
	UserService userService;
//	static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value={"/getUserList"}, method=RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList (){
		JsonResult r = new JsonResult();
		try {
			List<User> users = userService.getUserList();
			r.setResult(users);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");
			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

    /*
     * 我们通过@ApiOperation注解来给API增加说明、
     * 通过@ApiImplicitParams、@ApiImplicitParam注解来给参数增加说明。
     * 
     */
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<JsonResult> add (@RequestBody User user){
		JsonResult r = new JsonResult();
		try {
			int orderId = userService.add(user);
			if (orderId < 0) {
				r.setResult(orderId);
				r.setStatus("fail");
			} else {
				r.setResult(orderId);
				r.setStatus("ok");
			}
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserById (@PathVariable Long id){
		JsonResult r = new JsonResult();
		try {
			User user = userService.getUserById(id);
			r.setResult(user);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");
			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonResult> update (@PathVariable("id") Long id, @RequestBody User user){
		JsonResult r = new JsonResult();
		try {
			int ret = userService.update(id, user);
			if (ret < 0) {
				r.setResult(ret);
				r.setStatus("fail");
			} else {
				r.setResult(ret);
				r.setStatus("ok");
			}
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Long id){
		JsonResult r = new JsonResult();
		try {
			int ret = userService.delete(id);
			if (ret < 0) {
				r.setResult(ret);
				r.setStatus("fail");
			} else {
				r.setResult(ret);
				r.setStatus("ok");
			}
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}
	
}
