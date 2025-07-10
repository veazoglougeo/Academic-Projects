#pragma once
#include "sgg/graphics.h"
#include "gameobject.h"
#include "box.h"
#include "bullet.h"
#include <list>
#include "timer.h"
using namespace graphics;
using namespace std;

class Player : public GameObject, public Box, public Collidable {
	Brush m_brush_player;
	Brush m_brush_flame;
	list<Bullet*> m_bullets;
	Timer m_timer{ 1.0f, Timer::TIMER_ONCE };
	float m_velocity = 2.0f;
	int b_count = 0;
	float m_last_shot = 0.0f;
	int m_hp = 1;
	const float m_cooldown = 0.5f;
	bool is_moving = false;
	bool m_is_exploding = false;
	bool m_recently_hit = false;
	float m_explosion_timer = 0.0f; 
	int m_explosion_frame = 1;
public:
	Player(string name) : GameObject(name){}
	void update(float dt) override;
	void init() override;
	void draw() override;
	void markHit();
	bool recentlyHit() const;
	void resetHit();
	float getPosX() { return m_pos_x; }
	float getPosY() { return m_pos_y; }
	list<Bullet*>& getBullets();
	Disk getCollisionHull() const override;
	void setExploding(bool exploding) {
		m_is_exploding = exploding;
		m_explosion_timer = 0.0f;
		m_explosion_frame = 1;   
	}
	bool isExploding() const {
		return m_is_exploding;
	}
	void increaseHp() {
		m_hp++;
	}
	void decreaseHp() {
		if (m_hp > 0){
		m_hp--;
		}
	}
	void decreaseVelocity() {
		m_velocity -= 0.5f;
		
	}
	int getHp() {
		return m_hp;
	}
	void increaseVelocity() {
		m_velocity += 0.5f;
	}
	float getVelocity() const { return m_velocity; }
};