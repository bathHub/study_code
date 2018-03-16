package com.ali.entity;

import java.util.HashSet;
import java.util.Set;

public class Department {
private long id;
private  String name;
private String description;
private Department parent;//上级部门，只有一个
private Set<Department> childrens = new HashSet<Department>(0);
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Department getParent() {
	return parent;
}
public void setParent(Department parent) {
	this.parent = parent;
}
public Set<Department> getChildrens() {
	return childrens;
}
public void setChildrens(Set<Department> childrens) {
	this.childrens = childrens;
} 

}
