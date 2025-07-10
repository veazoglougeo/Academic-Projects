#pragma once
#include "sgg/graphics.h"
#include "gameobject.h"
#include "box.h"
#include "sgg/util.h"
using namespace graphics;
class PowerUp : public GameObject, public Box, public Collidable {
public:
	typedef enum {
		speed,
		health
	}Type;
	float m_velocity = 0.8f;
	float m_size = 2.0f;
	Brush m_powerup_brush;
	Type m_type;
public:
	PowerUp(float x, float size, Type type);
	void update(float dt) override;
	void draw();
	bool outOfBounds(float cH);
	float getX() { return m_pos_x; }
	float gety() { return m_pos_y; }
	void hit();
	Type getType() const { return m_type; }
	float getSize() { return m_size; }
	bool isDestroyed() const;
	Disk getCollisionHull() const override;
};