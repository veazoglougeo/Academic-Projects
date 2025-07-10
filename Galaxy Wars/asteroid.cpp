#include "asteroid.h"
#include "sgg/graphics.h"
#include "gamestate.h"

Asteroid::Asteroid(float  x, float size, float velocity) : GameObject("Asteroid"), m_velocity(velocity), m_size(size) {
	m_pos_x = x;
	m_pos_y = -m_size;
	if (size > 0.6f) {
		m_hp = 2;
	}
	else {
		m_hp = 1;
	}
}
/*Used to decrease 1 health point when an asteroid is hit.*/
void Asteroid::hit() {
	m_hp--;
}
/*Checks if an asteroid is destroyed to increase score, play sound and delete asteroid.*/
bool Asteroid::isDestroyed() const {
	return m_hp <= 0;
}
void Asteroid::update(float dt) {
	m_pos_y += m_velocity * dt / 1000.0f;
	GameObject::update(dt);
}
void Asteroid::draw() {
	m_orientation += rotation_speed;
	setOrientation(m_orientation);
	m_asteroid_brush.texture = m_state->getFullAssetPath("asteroid.png");
	m_asteroid_brush.outline_opacity = 0.0f;
	/*If a big asteroid has 1 health point left, paint it red.*/
	if (m_hp == 1 && m_size > 0.6f) {
		m_asteroid_brush.fill_color[0] = 1.0f;
		m_asteroid_brush.fill_color[1] = 0.3f;
		m_asteroid_brush.fill_color[2] = 0.3f;
	}
	drawRect(m_pos_x, m_pos_y, m_size, m_size, m_asteroid_brush);
}
bool Asteroid::outOfBounds(float cH) {
	return m_pos_y > cH + m_size;
}

Disk Asteroid::getCollisionHull() const
{
	Disk disk;
	disk.cx = m_pos_x;
	disk.cy = m_pos_y;
	disk.radius = m_size / 2.0f;
	return disk;
}