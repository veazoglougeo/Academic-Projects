#pragma once
#include "sgg/graphics.h"
#include "gameobject.h"
#include "box.h"
using namespace graphics;
class Asteroid : public GameObject, public Box, public Collidable {
	float m_velocity;
	float m_size;
	float m_orientation = 0.0f;
	float rotation_speed = 0.2f;
	Brush m_asteroid_brush;
	int m_hp = 1;
public: 
	Asteroid(float x, float size, float velocity);
	void update(float dt) override;
	void draw();
	bool outOfBounds(float cH);
	float getX() { return m_pos_x; }
	float getY() { return m_pos_y; }
	bool intersects(Box& other) {
		return Box::intersect(other); 
	}
	int getHp() { return m_hp; }
	void hit();
	float getSize() { return m_size; }
	bool isDestroyed() const;
	Disk getCollisionHull() const override;
	int getPoints() {
		if (m_size > 0.6f) {
			return 2;
		}
		else {
			return 1;
		}
	}
};