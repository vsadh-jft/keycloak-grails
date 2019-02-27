import React from "react";
import { TabContent, TabPane, Nav, NavItem, NavLink } from "reactstrap";
import classnames from "classnames";
import UserList from "./UserList";
import { connect } from "react-redux";
import { NotificationContainer } from "react-notifications";

import { addBlog, fetchListOfBlog } from "./action";
import "react-notifications/lib/notifications.css";
const tabs = ["user", "admin"];

const TabItem = props => (
  <NavItem>
    <NavLink
      className={classnames({ active: props.activeTab === props.title })}
      onClick={() => {
        props.toggle(props.title);
      }}
    >
      {props.title.charAt(0).toUpperCase() + props.title.slice(1)}
    </NavLink>
  </NavItem>
);

class Home extends React.Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      activeTab: "user",
      entity: {
        title: "",
        body: ""
      }
    };
  }

  handleInput = (e, key) => {
    let value = e.target.value;
    let entity = { ...this.state.entity };
    entity[key] = value;
    this.setState({ entity: entity });
  };

  submitForm = toggle => {
    const entity = this.state.entity;
    this.props.addBlog(entity);
    this.setState({ entity: {} });
    toggle();
  };

  componentDidMount() {
    this.props.fetchListOfBlog();
  }

  toggle(tab) {
    if (this.state.activeTab !== tab) {
      this.setState({
        activeTab: tab
      });
    }
  }

  renderNabBar() {
    return (
      <div className="tab-item">
        <Nav tabs>
          {tabs.map((tab, index) => (
            <TabItem
              key={index}
              activeTab={this.state.activeTab}
              title={tab}
              toggle={this.toggle}
            />
          ))}
          <button
            className="btn btn-success"
            onClick={this.props.kc.logout}
            style={{ float: "right" }}
          >
            Logout
          </button>
        </Nav>
      </div>
    );
  }

  render() {
    const username = this.props.kc.tokenParsed.preferred_username; //realm_access
    return (
      <div>
        {this.props.blog.loader ? <div className="processing-request" /> : null}

        {this.renderNabBar()}
        <h2>{`Welcome ${username}`}</h2>
        <TabContent activeTab={this.state.activeTab}>
          {tabs.map((tab, index) => (
            <TabPane tabId={tab} key={index}>
              <UserList
                data={this.props.blog.blogs}
                handleInput={this.handleInput}
                activeTab={this.state.activeTab}
                submitForm={this.submitForm}
              />
            </TabPane>
          ))}
        </TabContent>
        <NotificationContainer />
      </div>
    );
  }
}

const mapStateToProps = state => ({
  kc: state.keycloak,
  blog: state.blogReducer
});

const mapDispatchToProps = {
  addBlog,
  fetchListOfBlog
};
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);

// export default Home;
