#pragma once
#include "sgg/util.h"
#include <string>
class Collidable {
public:
	virtual Disk getCollisionHull() const = 0;
};
class GameObject
{
	static int m_next_id;
protected:
	class GameState* m_state;
	std::string m_name;
	int m_id = 0;
	bool m_active = true;
public:
	GameObject(const std::string& name = "");
	virtual void update(float dt) {}
	virtual void init() {}
	virtual void draw() {}
	virtual ~GameObject() {}
	bool outOffBounds(float cW, float cH);
	bool outOfBounds(float cH);
	bool isActive() { return m_active; }
	void setActive(bool a) { m_active = a; }
};
