#pragma once
#include "sgg/graphics.h"
#include "gameobject.h"
#include "box.h"
using namespace graphics;
class Bullet : public GameObject, public Box, public Collidable {
	float m_velocity = 5.0f;
	Brush m_bullet_brush;
public:
	Bullet(float x, float y);
	void update(float dt) override;
	void draw() override;
	bool outOfBounds(float cW, float cH);
	float getX() { return m_pos_x; }
	float getY() { return m_pos_x; }
	bool intersects(Box& other) {
		return Box::intersect(other);
	}
	Disk getCollisionHull() const override;
};