import React, { useState } from 'react';
import axios from 'axios';
import './Header.css';

function SignUp() {
  const [formData, setFormData] = useState({
    loginId: '',
    password: '',
    name: '',
    nickname: '',
    gender: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('http://localhost:5000/signup', formData)
      .then((response) => {
        console.log(response.data);
        // 회원가입 성공 시 필요한 처리
      })
      .catch((error) => {
        console.error('There was an error!', error);
      });
  };

  return (
    <div className="form-container">
      <h2>회원가입</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="loginId" placeholder="아이디" onChange={handleChange} required />
        <input type="password" name="password" placeholder="비밀번호" onChange={handleChange} required />
        <input type="text" name="name" placeholder="이름" onChange={handleChange} required />
        <input type="text" name="nickname" placeholder="닉네임" onChange={handleChange} required />
        <div className="gender">
          <label>
            <input type="radio" name="gender" value="male" onChange={handleChange} required />
            남성
          </label>
          <label>
            <input type="radio" name="gender" value="female" onChange={handleChange} required />
            여성
          </label>
        </div>
        <button type="submit">회원가입</button>
      </form>
    </div>
  );
}

export default SignUp;
